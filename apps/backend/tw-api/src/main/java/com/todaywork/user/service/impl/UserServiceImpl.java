package com.todaywork.user.service.impl;

import com.todaywork.domain.User;
import com.todaywork.dto.UserDto;
import com.todaywork.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jeminmac on 2016. 6. 24..
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource(name="userDAO")
	private UserDAO userDAO;
	@Autowired
	ModelMapper modelMapper;

	@Override
	@Modifying
	@Transactional
	public UserDto.Response insertUser(UserDto.Create userCreate) throws Exception{
		final User user = modelMapper.map(userCreate, User.class);
		user.getUserRoleList().stream().forEach(userRole -> userRole.setUser(user));
		return modelMapper.map(userDAO.save(user), UserDto.Response.class);
	}

	@Override
	@Transactional
	public PageImpl<UserDto.Response> findUserAll(Pageable pageable) {
		Page<User> page = userDAO.findAll(pageable);
		List<UserDto.Response> content = page.getContent().stream()
			.map(user -> modelMapper.map(user, UserDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	public UserDto.Response findUserOne(String email) {
		return modelMapper.map(userDAO.findByEmail(email),UserDto.Response.class);
	}

	@Override
	@Modifying
	@Transactional
	public UserDto.Response updateUser(UserDto.Update userUpdate) throws Exception{
		final User user = modelMapper.map(userUpdate, User.class);
		user.getUserRoleList().stream().forEach(userRole -> userRole.setUser(user));
		return modelMapper.map(userDAO.save(user), UserDto.Response.class);
	}

	@Override
	public void deleteUser(UserDto.Delete userDelete) {
		userDAO.delete(userDelete.getUniqueId());
	}

	@Override
	@Transactional
	public void updateEnabled(UserDto.Update userUpdate) {
		userDAO.updateEnabled(userUpdate.getUniqueId(),userUpdate.isEnabled());
	}

	@Override
	@Transactional
	public PageImpl<UserDto.Response> findUserSearch(UserDto.Search userSearch, Pageable pageable) {
		String likeExpression =null;
		Page<User> page;
		boolean enabled = false;

		if("true".equals(userSearch.getEnabled())){
			enabled = true;
		}
		if("all".equals(userSearch.getEnabled())){
			if(userSearch.getEmail() != null){
				likeExpression = "%" + userSearch.getEmail() + "%";
				page = userDAO.findByEmailIgnoreCaseContaining(likeExpression,pageable);
			}else{
				likeExpression = "%" + userSearch.getUserName() + "%";
				page = userDAO.findByUserNameIgnoreCaseContaining(likeExpression,pageable);
			}
		}else{
			if(userSearch.getEmail() != null){
				likeExpression = "%" + userSearch.getEmail() + "%";
				page = userDAO.findByEmailIgnoreCaseContainingAndEnabled(likeExpression,enabled, pageable);
			}else if(userSearch.getUserName() != null){
				likeExpression = "%" + userSearch.getUserName() + "%";
				page = userDAO.findByUserNameIgnoreCaseContainingAndEnabled(likeExpression,enabled, pageable);
			}else{
				page = userDAO.findByEnabled(enabled,pageable);
			}
		}

		List<UserDto.Response> content = page.getContent().stream()
			.map(user -> modelMapper.map(user, UserDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}
}
