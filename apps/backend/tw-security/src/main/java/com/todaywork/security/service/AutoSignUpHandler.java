package com.todaywork.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Transactional;

//@Component
public class AutoSignUpHandler implements ConnectionSignUp {
	private static final Logger logger = LoggerFactory.getLogger(AutoSignUpHandler.class);

/*	@Resource(name = "userDAO")
	private UserDAO userDAO;*/

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private volatile long userCount;

	@Override
	@Transactional
	public String execute(final Connection<?> connection) {
		logger.debug("CONNECTION PROVIDER USER ID : {}", connection.getKey().getProviderUserId());

		/*final UserEntity userEntity = new UserEntity();
		final Set<UserRoleEntity> userRoleEntitySet = new HashSet<>();
		final UserRoleEntity userRoleEntity = new UserRoleEntity();
		final UserSocialEntity userSocialEntity = new UserSocialEntity();

		UserProfile userProfile = connection.fetchUserProfile();
		userEntity.setUserName(userProfile.getFirstName() + ' ' + userProfile.getLastName());
		userEntity.setPassword(passwordEncoder.encode("carlab@carlab@" + connection.getKey().getProviderId()));
		userEntity.setEnabled(true);
		userEntity.setEmail(connection.getKey().getProviderUserId() + "@" + connection.getKey().getProviderId() + ".tmp.com");
		*//*if(!StringUtils.isEmpty(userProfile.getEmail())){
			userEntity.setEmail(userProfile.getEmail());
		}*//*

		userSocialEntity.setDisplayNm(connection.createData().getDisplayName());
		userSocialEntity.setProviderId(SocialProvider.valueOf(connection.getKey().getProviderId()));
		userSocialEntity.setProviderUserId(connection.getKey().getProviderUserId());
		userSocialEntity.setAccessToken(connection.createData().getAccessToken());
		userSocialEntity.setExpireTime(connection.createData().getExpireTime());
		userSocialEntity.setRefreshToken(connection.createData().getRefreshToken());
		userSocialEntity.setImageUrl(connection.createData().getImageUrl());
		userSocialEntity.setProfileUrl(connection.createData().getProfileUrl());
		userSocialEntity.setSecret(connection.createData().getSecret());
		userSocialEntity.setUser(userEntity);

		userEntity.setUserSocialEntity(userSocialEntity);

		userRoleEntity.setRole("ROLE_USER");
		userRoleEntity.setUser(userEntity);
		userRoleEntitySet.add(userRoleEntity);

		userEntity.setUserRoleEntity(userRoleEntitySet);

		userDAO.saveAndFlush(userEntity);
		//add new users to the db with its default roles for later use in SocialAuthenticationSuccessHandler
*//*        final User user = new User();
		user.setUsername(generateUniqueUserName(connection.fetchUserProfile().getFirstName()));
        user.setProviderId(connection.getKey().getProviderId());
        user.setProviderUserId(connection.getKey().getProviderUserId());
        user.setAccessToken(connection.createData().getAccessToken());
        grantRoles(user);
        userRepository.save(user);*//*
		return userEntity.getUniqueId().toString();*/
        return "";
	}

}
