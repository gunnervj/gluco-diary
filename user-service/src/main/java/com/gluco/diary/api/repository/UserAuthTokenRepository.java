package com.gluco.diary.api.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gluco.diary.api.repository.models.UserDTO;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class UserAuthTokenRepository {
	private HazelcastInstance hazelCastInstance;
	private static final String USER_TOKEN_MAP = "user-token-map";
	private IMap<String, UserDTO> tokenMap = null;
	
	@Autowired
	public UserAuthTokenRepository(HazelcastInstance hazelCastInstance) {
		this.hazelCastInstance = hazelCastInstance;
	}
	
	@PostConstruct
	public void init() {
		tokenMap = hazelCastInstance.getMap(USER_TOKEN_MAP);
	}
	
	public UserDTO getUserByToken(String token) {
		token = token.replace("Bearer", "").trim();
		return tokenMap.get(token);
	}
	
	public UserDTO addToken(String token, UserDTO user) {
		token = token.replace("Bearer", "").trim();
		return tokenMap.put(token, user);
	}
	
	public UserDTO removeToken(String token) {
		token = token.replace("Bearer", "").trim();
		return tokenMap.remove(token);
	}
}
