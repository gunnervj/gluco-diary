package com.gluco.diary.record.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value=Include.NON_EMPTY)
public class Error {
	private Integer code;
	private String message;
	private String details;
}
