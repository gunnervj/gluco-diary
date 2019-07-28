package com.gluco.diary.record.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.gluco.diary.record.security.service.StashErrorDecoder;

import feign.Feign;

@Configuration
public class BeanConfigs {
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return Feign.builder()
                .errorDecoder(new StashErrorDecoder());
	}


}
