package com.gluco.diary.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gluco.diary.gateway.client.exception.AuthException;
import com.gluco.diary.gateway.client.exception.UnknownException;

@FeignClient("user-service")
public interface UserServiceClient {

	@RequestMapping(value="/api/users/profile/validate", method= RequestMethod.GET)
	public String validate(@RequestHeader("Authorization") String authorizationToken) throws AuthException, UnknownException;
	
}
