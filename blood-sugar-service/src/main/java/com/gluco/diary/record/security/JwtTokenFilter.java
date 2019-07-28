package com.gluco.diary.record.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gluco.diary.record.api.constants.ERROR_CODES;
import com.gluco.diary.record.api.exceptions.InvalidTokenException;
import com.gluco.diary.record.api.model.Response;
import com.gluco.diary.record.security.service.UserServiceClient;
import com.gluco.diary.record.security.service.model.User;
import com.gluco.diary.record.api.model.Error;


@Component
@Order(1)
public class JwtTokenFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class.getName());
	private UserServiceClient userServiceClient;
	
	@Autowired
	public JwtTokenFilter(UserServiceClient userServiceClient) {
		this.userServiceClient = userServiceClient;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String token = resolveToken(httpServletRequest);
		if( !StringUtils.isEmpty(token) ) {
			try {
				Optional<Authentication> authOpt = validateToken(token);
				if (authOpt.isPresent()) {
					SecurityContextHolder.getContext().setAuthentication(authOpt.get());
				} else {
					throw new InvalidTokenException(ERROR_CODES.INVALID_TOKEN);
				}
			} catch (InvalidTokenException ex) {
				SecurityContextHolder.clearContext();
				Response response = new Response();
				List<Error> errorList = new ArrayList<>();
				response.setErrors(errorList);
						 
				Error error = createError(ex.getErrorCode());
				errorList.add(error);
				
				httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
				httpServletResponse.getWriter().write(convertObjectToJson(errorList));
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	private Optional<Authentication> validateToken(String token) {
		
		try {
			Optional<User> userOpt = userServiceClient.validate("Bearer " + token);
			if( userOpt.isPresent() ) {
				Authentication auth = new UsernamePasswordAuthenticationToken(userOpt.get(), null, userOpt.get().getAuthorities());
				return Optional.of(auth);
			}
		} catch (Exception ex) {
			LOGGER.error("Error while calling user details service to validate token. Error :" + ex.getMessage(),ex);
		}
		
		return Optional.empty();
	}
	
	private String resolveToken(HttpServletRequest req) {
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

	
    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
    
	private Error createError(ERROR_CODES errorType) {
		   Error error = new Error();
		   error.setCode(errorType.getCode());
		   error.setMessage(errorType.getMessage());
		   return error;
	}

}