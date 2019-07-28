package com.gluco.diary.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private Boolean isSuccess;
	private String token;
}
