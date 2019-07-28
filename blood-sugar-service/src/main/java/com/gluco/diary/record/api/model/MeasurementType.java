package com.gluco.diary.record.api.model;

public enum MeasurementType {
	PRE_MEAL("Pre_Meal"),
	POST_MEAL("Post_Meal");
	
	String type;
	
	private MeasurementType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static boolean isValid(String type) {
		boolean isValid = false;
		for(MeasurementType val : values()) {
			if(val.getType().equalsIgnoreCase(type)) {
				isValid = true;
				break;
			}
		}
		
		return isValid;
	}
}
