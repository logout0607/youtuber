package com.todaywork.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 권 오빈 on 2016. 6. 7..
 */
@Configuration
public class DefaultConfig  {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(1);
		http
			.authorizeRequests()
				.anyRequest().permitAll()
			.and()
				.csrf().disable()
			.headers()
				.frameOptions().disable();
	}*/
}
