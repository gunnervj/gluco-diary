package com.gluco.diary.api.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	  ROLE_FREE_USER, 
	  ROLE_PAID_USER;

	  public String getAuthority() {
	    return name();
	  }
	  
}