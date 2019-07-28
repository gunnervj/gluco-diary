package com.gluco.diary.api.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gluco.diary.api.constants.CommonConstants;
import com.gluco.diary.api.constants.ERROR_CODES;
import com.gluco.diary.api.constants.Role;
import com.gluco.diary.api.domain.LoginRequest;
import com.gluco.diary.api.domain.User;
import com.gluco.diary.api.exceptions.AlreadyExistException;
import com.gluco.diary.api.exceptions.InvalidTokenException;
import com.gluco.diary.api.exceptions.UnknownException;
import com.gluco.diary.api.exceptions.ValidationException;
import com.gluco.diary.api.repository.UserRepository;
import com.gluco.diary.api.repository.models.UserDTO;
import com.gluco.diary.api.security.JwtTokenProvider;
import com.mongodb.MongoWriteException;

@Service
public class UserService implements IUserService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public Boolean signUp(User user) {
	try {
			
			UserDTO existingUser = userRepository.findByEmail(user.getEmail());
			if (null == existingUser) {
				LOGGER.info("User does not exist. Creating one.");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				UserDTO userDto = new UserDTO();
				userDto.setEmail(user.getEmail());
				userDto.setGender(user.getGender());
				userDto.setPassword(user.getPassword());
				userDto.setName(user.getName());

				
				userDto.setRoles(Arrays.asList(new Role[] { Role.ROLE_FREE_USER }));
				createUserInDataBase(userDto);
								
				LOGGER.info("User Created =" + userDto.getId());
			} else {
				throw new AlreadyExistException(ERROR_CODES.USER_ALREADY_EXISTS);
			}
		} catch (MongoWriteException | org.springframework.dao.DuplicateKeyException ex) {
			LOGGER.error("Unknown Error while creating profile. Duplicate Key: " + ex.getMessage(), ex);
			if(ex.getMessage().toLowerCase().contains("email"))
				throw new ValidationException(ERROR_CODES.INVALID_EMAIL);
		} catch (AlreadyExistException ex) {
			LOGGER.error("User already exist :" + user.toString());
			throw ex;
		}catch (Exception ex) {
			LOGGER.error("Error while saving user. Error :" + ex.getMessage(), ex);
			throw new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
		}
		
		return true;
	}
	
	public void createUserInDataBase(UserDTO userDto) {
		userRepository.insert(userDto);
	}
	
	@Override
	public User getProfile(Principal loggedInUser) {
		UserDTO existingUser = userRepository.findByEmail(loggedInUser.getName());
		if( existingUser == null ) {
			throw new InvalidTokenException(ERROR_CODES.INVALID_LOGIN);
		}
		return transformUser(existingUser);
	}

	@Override
	public String login(LoginRequest request)  throws ValidationException {
		String token = "";
		System.out.println("Log in request recieved");
		try {
			UserDTO existingUser = userRepository.findByEmail(request.getEmail());
				
			if( null == existingUser  || !passwordEncoder.matches(request.getPassword(), existingUser.getPassword()) ) {
				throw new ValidationException(ERROR_CODES.INVALID_LOGIN);
			}
			
			if(!CollectionUtils.isEmpty(existingUser.getRoles())) {
				token = jwtTokenProvider.createToken(existingUser.getEmail(), existingUser.getRoles());
			}		
			
			existingUser.setToken(token);
			CompletableFuture.runAsync(() -> {
				try {
					userRepository.save(existingUser);
					LOGGER.info("Updated token to database");
				} catch(Exception ex) {
					LOGGER.error("Error while updating token to Database: Error :" + ex.getMessage());
				}
			});
		} catch (ValidationException ex) {
			LOGGER.debug("ValidationException while logging-in. Email :" + request.getEmail() );
			throw ex;
		} catch (Exception ex) {
			LOGGER.error("Unknown Error while logging in. Email :" + request.getEmail() + " || Error :" + ex.getMessage(), ex);
			throw new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
		}
			System.out.println("TOKEN =>" + token);
		return token;
	}

	@Override
	public Boolean logout(Principal loggedInUser) {
		UserDTO existingUser = userRepository.findByEmail(loggedInUser.getName());
		
		if( existingUser == null ) {
			throw new InvalidTokenException(ERROR_CODES.INVALID_TOKEN);
		}
		
		try {
			existingUser.setToken(CommonConstants.EMPTY_STRING);
			userRepository.save(existingUser);
		} catch (MongoWriteException | org.springframework.dao.DuplicateKeyException ex) {
			LOGGER.error("Unknown Error while updating profile. Duplicate Key: " + ex.getMessage(), ex);
			if(ex.getMessage().toLowerCase().contains("email"))
				throw new ValidationException(ERROR_CODES.INVALID_EMAIL);
		} catch (Exception ex) {
			LOGGER.error("Unknown Error while logging out. Exception: " + ex.getMessage(), ex);
			throw new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
		}
		
		return true;
	}
	
	private User transformUser(UserDTO userDto) {
		User user = new User();
		if(null != userDto) {
			user.setEmail(userDto.getEmail());
			user.setName(userDto.getName());
			user.setGender(userDto.getGender());
		}
		return user;
		
	}


}
