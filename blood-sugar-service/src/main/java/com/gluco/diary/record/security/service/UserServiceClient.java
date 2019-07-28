package com.gluco.diary.record.security.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gluco.diary.record.api.exceptions.AuthException;
import com.gluco.diary.record.api.exceptions.UnknownException;
import com.gluco.diary.record.security.service.model.User;

@FeignClient("user-service")
public interface UserServiceClient {

	@RequestMapping(value="/api/users/profile/validate", method= RequestMethod.GET)
	public Optional<User> validate(@RequestHeader("Authorization") String authorizationToken) throws AuthException, UnknownException;
	
}
