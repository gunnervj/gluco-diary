package com.gluco.diary.api.constants;

public enum ERROR_CODES {
	UNDER_MAINTENANCE("We are under Maintenance and is currently unavailable",  101),
	USER_ALREADY_EXISTS("User already exist. If you have forgotten the password, please use forgot password.", 102),
	INVALID_REQUEST("Invalid Request.", 103),
	INVALID_LOGIN("Invalid login!!! Please Try Again.", 104),
	INVALID_TOKEN("Expired or invalid token. Please login again.", 105),
	INVALID_EMAIL("This email cannot be used, choose another.", 106);
	 
	private final String message;
    private final Integer code;

    private ERROR_CODES(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}
}
