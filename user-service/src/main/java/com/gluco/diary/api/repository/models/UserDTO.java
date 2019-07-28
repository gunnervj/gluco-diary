package com.gluco.diary.api.repository.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gluco.diary.api.constants.Role;

import lombok.Data;

@Data
@Document(collection="users")
public class UserDTO {
	@Id
	private String id;
	private String name;
	private String gender;
	@Indexed(unique=true)
	private String email;
	private String password;
	private String token;
	private List<Role> roles;
}