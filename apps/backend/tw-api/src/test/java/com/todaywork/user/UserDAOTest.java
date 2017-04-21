package com.todaywork.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.domain.User;
import com.todaywork.domain.UserRole;
import com.todaywork.dto.UserDto;
import com.todaywork.dto.UserRoleDto;
import com.todaywork.user.service.impl.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by 권 오빈 on 2016. 6. 7..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class UserDAOTest {

	private UserDto.Create userCreate;

	@Autowired private UserDAO userDAO;
	@Autowired private ModelMapper modelMapper;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private ObjectMapper objectMapper;

	@Before
	public void setUp(){
		userCreate = new UserDto.Create();
		userCreate.setEmail("test@test.co.kr");
		userCreate.setUserName("테스트");
		userCreate.setPassword(passwordEncoder.encode("test"));

		List<UserRoleDto.Create> userRoleList = new ArrayList<>();
		UserRole userRole = new UserRole();

		userRole.setRole("ROLE_ADMIN");
		//userRole.setUser(modelMapper.map(userCreate,User.class));
		userRoleList.add(modelMapper.map(userRole,UserRoleDto.Create.class));

		userRole = new UserRole();
		userRole.setRole("ROLE_USER");
		//userRole.setUser(modelMapper.map(userCreate,User.class));
		userRoleList.add(modelMapper.map(userRole,UserRoleDto.Create.class));
		userCreate.setUserRoleList(userRoleList);

	}

	@Test
	@Modifying
	public void 유저_등록_테스트(){
		final User user = modelMapper.map(userCreate, User.class);

		user.getUserRoleList().stream().forEach(userRole -> userRole.setUser(user));

		User returnUser = userDAO.save(user);
		// 등록 후 등록한 유저 목록을 가지고 왔을 때, 동일하다.
		assertThat(returnUser.getEmail(), is(equalTo("test@test.co.kr")));
		assertThat(returnUser.getUserRoleList().get(0).getRole(), is(equalTo("ROLE_ADMIN")));
		assertThat(returnUser.getUserRoleList().get(1).getRole(), is(equalTo("ROLE_USER")));

		// 암호화 된 비밀번호 확인
		log.info("비밀번호 : {}", returnUser.getPassword());

		log.info("CREATE_DATE : {}", returnUser.getCreatedDate());
		log.info("LAST MODIFIED DATE : {}", returnUser.getLastModifiedDate());

	}

	@Test
	public void 유저_목록_테스트() throws JsonProcessingException {
		//유저_등록_테스트();
		List<UserDto.Response> userList = userDAO.findAll(new PageRequest(0, 3)).getContent().stream().map(
			user -> modelMapper.map(user, UserDto.Response.class)
		).collect(Collectors.toList());

		assertThat(userList.get(0).getUserRoleList().size(), is(equalTo(2)));

		log.info("JSON : {}", objectMapper.writeValueAsString(userList));
	}

	@Test
	public void 유저_삭제_테스트() {
		유저_등록_테스트();

		User user = userDAO.findByEmail("test@test.co.kr");

		UserDto.Delete userDelete = new UserDto.Delete();
		userDelete.setUniqueId(user.getUniqueId());
		userDAO.delete(userDelete.getUniqueId());
		assertNull(userDAO.findByEmail("test@test.co.kr"));
	}

	@Test
	@Modifying
	public void 유저_수정_테스트() {
		유저_등록_테스트();

		User user = userDAO.findByEmail("test@test.co.kr");

		UserDto.Update userUpdate = new UserDto.Update();
		userUpdate.setUniqueId(user.getUniqueId());
		userUpdate.setPassword(user.getPassword());
		userUpdate.setUserName("수정 변경");

		user = userDAO.save(modelMapper.map(userUpdate, User.class));

		assertThat(user.getUserName(), is(equalTo("수정 변경")));
		log.info("변경 후 이름 : {}", user.getUserName());
	}

	@Test(expected = ConstraintViolationException.class)
	public void Exception_테스트() {
		User user = new User();
		user.setEmail("잘못된 이메일 형식");
		userDAO.save(user);
	}
}
