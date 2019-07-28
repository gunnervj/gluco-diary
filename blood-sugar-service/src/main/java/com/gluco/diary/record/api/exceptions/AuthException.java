package com.gluco.diary.record.api.exceptions;

import com.gluco.diary.record.api.constants.ERROR_CODES;

public class AuthException extends Exception{

	private static final long serialVersionUID = 4081210403603394222L;
	private ERROR_CODES errorCode;
	
	public AuthException(ERROR_CODES errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}
	
	

}
