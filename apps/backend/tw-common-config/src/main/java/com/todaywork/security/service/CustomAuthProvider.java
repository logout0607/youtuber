package com.todaywork.security.service;

import com.todaywork.domain.User;
import com.todaywork.security.service.impl.SecurityUserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 권 오빈 on 2016. 1. 26
 */
@Component
public class CustomAuthProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthProvider.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CmmSocialAndUserDetailService userDetailsService;

	@Resource(name = "securityUserDAO")
	private SecurityUserDAO userDAO;

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Transactional
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.debug("Authentication : {} ", authentication.toString());
		String email = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		logger.debug("비밀번호: {}", password);

		logger.debug("사용자가 입력한 로그인 정보 입니다. {}", Arrays.asList(email, passwordEncoder.encode(password)));

		List<User> userEntityList = userDAO.findByEmailAndEnabled(email, true);

		if(userEntityList.size() == 0){
			throw new BadCredentialsException("사용자 정보가 없습니다.");
		}

		User user = userEntityList.get(0);

		if(!passwordEncoder.matches(password, user.getPassword())){
			throw new BadCredentialsException("사용자 정보가 없습니다.");
		}
		logger.debug("최초 권한 : {}", user.getUserRoleList().toString());
		CmmUserDetails cud = (CmmUserDetails) userDetailsService.loadUserByUniqueId(user.getUniqueId());

		logger.debug("이메일 주소 : {}", user.getEmail());
		logger.debug("비밀번호 : {}", user.getPassword());
		logger.debug("이름 : {}", user.getUserName());
		logger.debug("권한 : {}", user.getUserRoleList().toString());

		return new UsernamePasswordAuthenticationToken(cud, user.getUniqueId(), cud.getAuthorities());
	}

}
