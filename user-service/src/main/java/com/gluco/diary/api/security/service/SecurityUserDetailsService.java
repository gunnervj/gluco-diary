package com.gluco.diary.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gluco.diary.api.constants.ERROR_CODES;
import com.gluco.diary.api.repository.UserRepository;
import com.gluco.diary.api.repository.models.UserDTO;

@Service
public class SecurityUserDetailsService {

	  @Autowired
	  private UserRepository userRepository;

	  public UserDetails loadUserByEmailAndToken(String email, String token) throws UsernameNotFoundException {
	    final UserDTO user = userRepository.findByEmailAndToken(email, token);

	    if (user == null || StringUtils.isEmpty(user.getToken())) {
	      throw new UsernameNotFoundException(ERROR_CODES.INVALID_LOGIN.getMessage());
	    }

	    return org.springframework.security.core.userdetails.User//
	        .withUsername(email)
	        .password(user.getPassword())
	        .authorities(user.getRoles())
	        .accountExpired(false)
	        .accountLocked(false)
	        .credentialsExpired(false)
	        .disabled(false)
	        .build();
	  }
	  
}
