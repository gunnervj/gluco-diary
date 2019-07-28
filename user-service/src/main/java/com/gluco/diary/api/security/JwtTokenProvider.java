package com.gluco.diary.api.security;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.gluco.diary.api.constants.ERROR_CODES;
import com.gluco.diary.api.constants.Role;
import com.gluco.diary.api.exceptions.InvalidTokenException;
import com.gluco.diary.api.security.service.SecurityUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class.getName());
	private SecretKey secretKey;
	@Value("${token.validity}")
	private long validityInMilliseconds = 3600000; // 1h
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
	}
	
	public String createToken(String email, List<Role> roles) {

		Claims claims = Jwts.claims().setSubject(email);
		if(!CollectionUtils.isEmpty(roles)) {
			claims.put("auth", roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
					.filter(Objects::nonNull)
					.collect(Collectors.toList()));
		} else {
			claims.put("auth", Role.ROLE_FREE_USER);
		}
		
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	
	public String resolveToken(HttpServletRequest req) {
		Enumeration<String> headerNames = req.getHeaderNames();
		LOGGER.debug("Resolving headers");
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			LOGGER.debug("Current header =>" +headerName);
			if(headerName.toLowerCase().equals("authorization")) {
				String bearerToken =req.getHeader(headerName);
				if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
					return bearerToken.substring(7);
				}
				break;
			}
		}
		return null;
	}
	

	public Authentication validateToken(String token) {
		try {
			
			if( null == token ) {
				throw new InvalidTokenException(ERROR_CODES.INVALID_TOKEN);
			}
			
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			UserDetails userDetails = securityUserDetailsService.loadUserByEmailAndToken(getEmail(token), token);
			
			if( null == userDetails ) {
				throw new InvalidTokenException(ERROR_CODES.INVALID_TOKEN);
			}
			
			return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		} catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
			LOGGER.error("Token Invalid: " + e.getMessage());
			throw new InvalidTokenException(ERROR_CODES.INVALID_TOKEN);
		}
	}
}
