package com.todaywork.security.service.impl;

import com.todaywork.domain.User;
import com.todaywork.security.service.CmmSocialAndUserDetailService;
import com.todaywork.security.service.CmmUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 4. 25..
 */
@Service("cmmUserDetailsService")
public class CmmSocialAndUserDetailsServiceImpl implements CmmSocialAndUserDetailService {
	private static final Logger logger = LoggerFactory.getLogger(CmmSocialAndUserDetailService.class);

	@Resource(name = "securityUserDAO")
	private SecurityUserDAO userDAO;

	@Override
	@Transactional(readOnly = true)
	public CmmUserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
		logger.debug("uniqueId : {}", uniqueId);
		logger.debug("UUID : {}", UUID.fromString(uniqueId));
		User user = userDAO.findOne(UUID.fromString(uniqueId));

		if(user == null){
			throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		logger.debug("ROLE : {}", user.getUserRoleList().toString());
		user.getUserRoleList().forEach(role ->
			authorities.add(new SimpleGrantedAuthority(role.getRole()))
		);

		return new CmmUserDetails(
			user.getUniqueId().toString(),
			user.getPassword(),
			user.isEnabled(),
			user.isEnabled(),
			user.isEnabled(),
			user.isEnabled(),
			authorities,
			user
		);
	}

	@Override
	public UserDetails loadUserByUniqueId(UUID uuid) throws UsernameNotFoundException {

		User user = userDAO.findOne(uuid);
		logger.debug("ROLE : {}", user.getUserRoleList().toString());

		return loadUserByUsername(uuid.toString());
	}

	/*@Override
	public UserEntity loadUserByConnectionKey(ConnectionKey connectionKey) {
		UserEntity userEntity = userDAO.findByUserSocialEntity_ProviderIdAndUserSocialEntity_ProviderUserId(SocialProvider.valueOf(connectionKey.getProviderId()), connectionKey.getProviderUserId());
		return checkUser(userEntity);
	}

	@Override
	public void updateUserSocial(UserEntity userEntity) {
		userDAO.saveAndFlush(userEntity);
	}*/

	@Override
	@Transactional(readOnly = true)
	public User findByUserId(String userId) {
		return userDAO.findOne(UUID.fromString(userId));
	}

	/*@Override
	public SocialUserDetails loadUserByUserId(String uniqueId) throws UsernameNotFoundException {
		logger.debug("SOCIAL USER ID : {}", uniqueId);
		return loadUserByUsername(uniqueId);
	}*/

	private User checkUser(User user){
		if(user == null)
			throw new UsernameNotFoundException("");
		return user;
	}
}
