package com.gluco.diary.record.api.constants;

public enum ERROR_CODES {
	UNDER_MAINTENANCE("We are under Maintenance and is currently unavailable",  101),
	READING_NOT_FOUND("No reading(s) found", 102),
	INVALID_REQUEST("Invalid Request.", 103),
	INVALID_TOKEN("Expired or invalid token. Please login again.", 105),
	FREQUENCY_REQUIRED("When is this recorded for? Morning, Afternoon or Night?", 106),
	MEASUREMENT_REQUIRED("Is this After or Before Food?", 107),
	INVALID_DATE("Date cannot be past today or empty.", 108);
	 
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
