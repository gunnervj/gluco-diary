package com.gluco.diary.record.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
		http.cors().and().authorizeRequests()
			.antMatchers("/").permitAll()
	        .antMatchers("/hystrix.stream/**").permitAll()  
	        .antMatchers("/metrics/**").permitAll()
	        .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated();
       http.addFilterAfter(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/v2/api-docs")
	        .antMatchers("/swagger-resources/**")
	        .antMatchers("/swagger-ui.html")
	        .antMatchers("/configuration/**")
	        .antMatchers("/webjars/**")
	        .antMatchers("/public");
	}

}