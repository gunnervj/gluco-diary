package com.gluco.diary.api.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;


@Data
@JsonInclude(value=Include.NON_EMPTY)
public class Response {
	
	private Boolean success;
	private Class<?> payload;
	private List<Error> errors;

}
