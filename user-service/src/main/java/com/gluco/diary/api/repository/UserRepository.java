package com.gluco.diary.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gluco.diary.api.repository.models.UserDTO;

public interface UserRepository extends MongoRepository<UserDTO, String> {
	
	public UserDTO findByEmail(String email);
	public UserDTO findByEmailAndToken(String email, String token);

}
