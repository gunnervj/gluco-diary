package com.gluco.diary.api.exceptions;

import com.gluco.diary.api.constants.ERROR_CODES;

public class AlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -3030941259780401336L;
	private ERROR_CODES errorCode;
	
	public AlreadyExistException(ERROR_CODES errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public AlreadyExistException(String message,Throwable cause) {
		super(message, cause);
	}
	

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}	

}