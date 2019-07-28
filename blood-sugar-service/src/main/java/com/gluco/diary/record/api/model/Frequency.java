package com.gluco.diary.record.api.model;

public enum Frequency {

	Morning("Morning"),
	AfterNoon("Afternoon"),
	Night("Night");
	
	String frequency;
	
	private Frequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequency() {
		return frequency;
	}
	
	
	public static boolean isValid(String frequency) {
		boolean isValid = false;
		for(Frequency val : values()) {
			if(val.getFrequency().equalsIgnoreCase(frequency)) {
				isValid = true;
				break;
			}
		}
		
		return isValid;
	}
}
