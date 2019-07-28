package com.gluco.diary.api.service;

import java.security.Principal;

import com.gluco.diary.api.domain.LoginRequest;
import com.gluco.diary.api.domain.User;

public interface IUserService {
	
	Boolean signUp(User user);
	
	User getProfile(Principal loggedInUser);
	
	String login(LoginRequest request);
	
	Boolean logout(Principal loggedInUser);
}
