package com.gluco.diary.api.exceptions;

import com.gluco.diary.api.constants.ERROR_CODES;

public class ValidationException extends RuntimeException{
	private static final long serialVersionUID = 2027453082604370968L;
	private ERROR_CODES errorCode;
	
	public ValidationException(ERROR_CODES errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ValidationException(String message,Throwable cause) {
		super(message, cause);
	}
	

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}	

}
