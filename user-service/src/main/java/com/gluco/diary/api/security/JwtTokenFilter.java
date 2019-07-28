package com.gluco.diary.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gluco.diary.api.constants.ERROR_CODES;
import com.gluco.diary.api.domain.Response;
import com.gluco.diary.api.exceptions.InvalidTokenException;
import com.gluco.diary.api.domain.Error;

public class JwtTokenFilter extends OncePerRequestFilter {
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(httpServletRequest);
		if( !StringUtils.isEmpty(token) ) {
			try {
				Authentication auth = jwtTokenProvider.validateToken(token);
				if (null != auth) {
					SecurityContextHolder.getContext().setAuthentication(auth);
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