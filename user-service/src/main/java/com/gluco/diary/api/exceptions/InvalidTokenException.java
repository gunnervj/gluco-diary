package com.gluco.diary.api.exceptions;

import com.gluco.diary.api.constants.ERROR_CODES;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 3581285015177284781L;
	private ERROR_CODES errorCode;
	
	public InvalidTokenException(ERROR_CODES errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public InvalidTokenException(String message,Throwable cause) {
		super(message, cause);
	}

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}
}

