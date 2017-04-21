package com.todaywork.security.service;

import com.todaywork.domain.User;
import com.todaywork.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 2. 1
 */
@Slf4j
@Component
public class CmmLoginHelper {

	private static Environment environment;

	private static ModelMapper modelMapper;

	@Autowired
	public CmmLoginHelper(Environment environment, ModelMapper modelMapper){
		CmmLoginHelper.environment = environment;
		CmmLoginHelper.modelMapper = modelMapper;
	}

	public static UserDto.Session getUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (ObjectUtils.isEmpty(authentication) ||
				authentication.getAuthorities().size() == 0 ||
				authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {

			log.info("DEFAULT_USER_UNIQUE_ID: {}", environment.getProperty("defaultUserUniqueId"));
			//if(ObjectUtils.isEmpty(authentication.getPrincipal())){
			if(environment.getProperty("defaultUserUniqueId") != null){
				User user = new User();
				user.setUniqueId(UUID.fromString(environment.getProperty("defaultUserUniqueId")));
				//UserDto.Session sessionUser = modelMapper.map(user, UserDto.Session.class);
				return modelMapper.map(user, UserDto.Session.class);
			}

			//}

			return null;

		} else{
			CmmUserDetails cud = (CmmUserDetails) authentication.getPrincipal();
			User user = ((User) cud.getUser());
			Hibernate.initialize(user);
			UserDto.Session sessionUser = modelMapper.map(user, UserDto.Session.class);
			return sessionUser;
		}

	}

}
