package com.gluco.diary.record.api.exceptions;

import com.gluco.diary.record.api.constants.ERROR_CODES;

public class UnknownException extends RuntimeException {
	private static final long serialVersionUID = 413266406330784196L;
	private ERROR_CODES errorCode;
	
	public UnknownException(ERROR_CODES errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

	public ERROR_CODES getErrorCode() {
		return errorCode;
	}
	
	
	
}
