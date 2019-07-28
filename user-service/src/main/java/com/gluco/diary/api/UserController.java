package com.gluco.diary.api;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluco.diary.api.constants.CommonConstants;
import com.gluco.diary.api.domain.LoginRequest;
import com.gluco.diary.api.domain.LoginResponse;
import com.gluco.diary.api.domain.User;
import com.gluco.diary.api.service.IUserService;



@RestController
@RequestMapping(path=CommonConstants.BASE_PATH)
public class UserController implements UserAPI {
	
	private IUserService userService;
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public ResponseEntity<?> createUser(@Valid User request) {
		userService.signUp(request);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<User> profile(Principal loggedInUser) {
		User user = userService.getProfile(loggedInUser);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<LoginResponse> loginUser(@Valid LoginRequest request) {
		String token = userService.login(request);
		HttpHeaders headers = new HttpHeaders();
		headers.add("token", token);
		LoginResponse response = new LoginResponse(true, token);
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> logout(Principal loggedInUser) {
		userService.logout(loggedInUser);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> validate(Principal loggedInUser) {
		User user = new User();
		user.setEmail(loggedInUser.getName());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
