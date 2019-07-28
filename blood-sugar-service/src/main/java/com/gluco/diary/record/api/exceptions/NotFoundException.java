package com.gluco.diary.record.api.exceptions;

import com.gluco.diary.record.api.constants.ERROR_CODES;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = -3030941259780401336L;
	private ERROR_CODES errorCode;
	
	public NotFoundException(ERROR_CODES errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public NotFoundException(String message,Throwable cause) {
		super(message, cause);
	}
	

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}	

}