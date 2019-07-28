package com.gluco.diary.api;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gluco.diary.api.constants.CommonConstants;
import com.gluco.diary.api.domain.LoginRequest;
import com.gluco.diary.api.domain.LoginResponse;
import com.gluco.diary.api.domain.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Service for User signup, profile retrival, updation, verification etc.", basePath=CommonConstants.BASE_PATH, tags= "User")
public interface UserAPI {

	@RequestMapping(path= CommonConstants.REGISTER_URL, method= RequestMethod.POST)
	@ApiOperation(value="Service to create a new user.")
	@ApiResponses( value= {
			@ApiResponse(code=201, message="Success"),
			@ApiResponse(code=400, message="Invalid Input", response=Error.class),
			@ApiResponse(code=503, message="Under Maintenance", response=Error.class)
	})
	public ResponseEntity<?> createUser(@RequestBody @Valid User request);
	
	@RequestMapping(path=CommonConstants.PROFILE_URL, method= RequestMethod.GET)
	@ApiResponses( value= {
			@ApiResponse(code=200, message="Success", response = User.class),
			@ApiResponse(code=400, message="Invalid Input", response=Error.class),
			@ApiResponse(code=503, message="Under Maintenance", response=Error.class)
	})
	@ApiOperation(value="Service to get user profile.")
	public ResponseEntity<User> profile(@ApiIgnore Principal loggedInUser);
	
	@RequestMapping(path=CommonConstants.LOGIN_URL, method= RequestMethod.POST)
	@ApiOperation(value="Service to help user login.")
	@ApiResponses( value= {
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=400, message="Invalid Input", response=Error.class),
			@ApiResponse(code=503, message="Under Maintenance", response=Error.class)
	})
	public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest request);
	
	
	@ApiOperation(value="Service to help user logout.")
	@RequestMapping(path=CommonConstants.LOGOUT_URL, method= RequestMethod.PATCH)
	public ResponseEntity<?> logout(@ApiIgnore Principal loggedInUser);
	
	@ApiOperation(value="Service to help user validation.")
	@RequestMapping(path="/profile/validate", method= RequestMethod.GET)
	public ResponseEntity<User> validate(@ApiIgnore Principal loggedInUser);
}
