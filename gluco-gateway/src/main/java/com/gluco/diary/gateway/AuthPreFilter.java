package com.gluco.diary.gateway;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.gluco.diary.gateway.client.UserServiceClient;
import com.gluco.diary.gateway.client.exception.AuthException;
import com.gluco.diary.gateway.constants.ERROR_CODES;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AuthPreFilter extends ZuulFilter {
	
	@Autowired
	private UserServiceClient userServiceClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthPreFilter.class.getName());

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String url = null != ctx.getRequest().getRequestURL() ? ctx.getRequest().getRequestURL().toString() : "";
		LOGGER.info("URL =>" +url);
	    if (url.contains("register") || url.contains("login")) {
	    	LOGGER.info("Should Filter is set to false");
	        return false;
	    }
	    return true;
	}

	@Override
	public Object run() throws ZuulException {
		LOGGER.info("Initializing Pre Filter Auth");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		String authHeader = request.getHeader("Authorization");
		LOGGER.info(authHeader);
	    if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
	    	System.out.println("EMpty Auth");
	        ctx.setResponseStatusCode(401);
	        ctx.setSendZuulResponse(false);
	    } else {
	    	String userName = callUserService(authHeader, ctx);
	    	 if (!StringUtils.isEmpty(userName)) {
	        	 LOGGER.info("User logged in : " + userName + " With Auth Token : " + authHeader);
	        	 ctx.addZuulRequestHeader("Authorization", authHeader);
	        	 LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

	         }
	    }
		return null;
	}
	
	private String callUserService(String authHeader, RequestContext ctx) {
		String userName = "";
		
		try {
			userName = userServiceClient.validate(authHeader);
		} catch (AuthException e) {
			LOGGER.error("Error while calling userService Client");
			 ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
             ctx.setResponseBody(ERROR_CODES.INVALID_TOKEN.getMessage());
             ctx.setSendZuulResponse(false);
		} catch (Exception e) {
			 ctx.setResponseStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
             ctx.setResponseBody(ERROR_CODES.UNDER_MAINTENANCE.getMessage());
             ctx.setSendZuulResponse(false);
		}
		
		return userName;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 10;
	}
}
