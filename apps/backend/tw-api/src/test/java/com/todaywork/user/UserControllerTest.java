package com.todaywork.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.domain.User;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jeminmac on 2016. 6. 24..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private UserController userController;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired private UserDAO userDAO;

	private UserDto.Create userCreate;
	private UserDto.Delete userDelete;
	List<UserRoleDto.Create> userRoleList = new ArrayList<>();


	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();

		//등록 setup
		userCreate = new UserDto.Create();
		userCreate.setEmail("test@test.co.kr");
		userCreate.setUserName("박제민");
		userCreate.setPassword(passwordEncoder.encode("test"));

		UserRoleDto.Create userRoleCreate = new UserRoleDto.Create();
		userRoleCreate.setRole("ROLE_ADMIN");

		userRoleList.add(userRoleCreate);

		userRoleCreate = new UserRoleDto.Create();
		userRoleCreate.setRole("ROLE_USER");

		userRoleList.add(userRoleCreate);

		userCreate.setUserRoleList(userRoleList);

	}

	/**
	 * 유저를 등록시킨다
	 * url /user/find-all
	 * @throws Exception
	 */
	@Test
	public void 유저_등록_컨트롤러_테스트() throws JsonProcessingException {
		log.info("유저_등록_컨트롤러_테스트 시작");
		log.info("=====================================================================");
		try {
			//데이터가 없는경우에 save 한다
			if(userDAO.findByEmail(userCreate.getEmail()) == null) {
				this.mockMvc.perform(post("/user/save")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(userCreate)))
					// 처리 내용을 출력합니다.
					.andDo(print())
					.andExpect(status().isOk());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 유저 목록을 불러온다
	 * url /user/find-all
	 * @throws Exception
	 */
	@Test
	public void 유저_목록_컨트롤러_테스트() throws Exception {
		log.info("유저_목록_컨트롤러_테스트 시작");
		log.info("=====================================================================");
		try {
			this.mockMvc.perform(get("/user/find-all")
				.contentType(MediaType.APPLICATION_JSON))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 유저 하나 선택 테스트
	 * url /user/find-all
	 * @throws Exception
	 */
	@Test
	public void 유저_하나_선택_컨트롤러_테스트() throws Exception {
		log.info("유저_하나_선택_컨트롤러_테스트 시작");
		log.info("=====================================================================");
		try {
			this.mockMvc.perform(get("/user")
				.param("email", "test-init@test.co.kr"))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 유저 수정 컨트롤러 테스트
	 * url /user/save
	 * @throws Exception
	 */
	@Test
	public void 유저_수정_컨트롤러_테스트() throws Exception {
		log.info("유저_수정_컨트롤러_테스트 시작");
		log.info("=====================================================================");

		//업데이트 셋팅
		유저_등록_컨트롤러_테스트();

		User user = userDAO.findByEmail("jemin@test.co.kr");

		UserDto.Update userUpdate = modelMapper.map(user,UserDto.Update.class);
		userUpdate.setUniqueId(user.getUniqueId());
		userUpdate.setPassword(user.getPassword());
		userUpdate.setUserName("수정 변경");

		try {
			this.mockMvc.perform(patch("/user/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userUpdate)))
				// 처리 내용을 출력합니다.
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 유저 완전삭제를 한다
	 * url /user/delete
	 * @throws Exception
	 */
	@Test
	public void 유저_완전_삭제_컨트롤러_테스트() throws Exception {
		log.info("유저_완전_삭제_컨트롤러_테스트 시작");
		log.info("=====================================================================");
		//업데이트 셋팅
		유저_등록_컨트롤러_테스트();

		User user = userDAO.findByEmail("jemin@test.co.kr");
		UserDto.Delete userDelete = modelMapper.map(user,UserDto.Delete.class);
		try {
			this.mockMvc.perform(post("/user/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userDelete)))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 유저 enabled update 테스트
	 * url /user/update-enabled
	 * @throws Exception
	 */
	@Test
	public void 유저_Enabled_수정_컨트롤러_테스트() throws Exception {
		log.info("유저_Enabled_수정_컨트롤러_테스트 시작");
		log.info("=====================================================================");

		//업데이트 셋팅
		유저_등록_컨트롤러_테스트();

		User user = userDAO.findByEmail("jemin@test.co.kr");
		user.setEnabled(false);
		UserDto.Update userUpdate = modelMapper.map(user,UserDto.Update.class);

		try {
			this.mockMvc.perform(patch("/user/update-enabled")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userUpdate)))
				// 처리 내용을 출력합니다.
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 이메일/이름/enabled 로 검색할수있다
	 * url /user/search
	 * @throws Exception
	 */
	@Test
	public void 유저_검색_컨트롤러_테스트() throws Exception {
		log.info("유저_검색_컨트롤러_테스트 시작");
		log.info("=====================================================================");

		유저_등록_컨트롤러_테스트();

		UserDto.Search userSearch = new UserDto.Search();
		userSearch.setEmail("jemin");
		try {
			this.mockMvc.perform(post("/user/search")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userSearch)))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

		userSearch = new UserDto.Search();
		userSearch.setUserName("이현석");
		userSearch.setEnabled("true");
		try {
			this.mockMvc.perform(post("/user/search")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userSearch)))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

		userSearch = new UserDto.Search();
		userSearch.setEnabled("true");
		try {
			this.mockMvc.perform(post("/user/search")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userSearch)))
				// 처리 내용을 출력합니다.
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 유저를 여러명 등록시킨다 목록 테스트 용
	 * url /user/save
	 * @throws Exception
	 */
	@Test
	public void 유저_다수등록_컨트롤러_테스트() throws JsonProcessingException {
		log.info("유저_다수등록_컨트롤러_테스트 시작");
		log.info("=====================================================================");
		for(int i = 0; i < 30; i++){
			//등록 setup
			userCreate = new UserDto.Create();
			userCreate.setEmail("hyunsuk@test.co.kr" +i);
			userCreate.setUserName("이현석" +i);
			userCreate.setPassword(passwordEncoder.encode("test"));

			UserRoleDto.Create userRoleCreate = new UserRoleDto.Create();
			userRoleCreate.setRole("ROLE_ADMIN");

			userRoleList.add(userRoleCreate);

			userRoleCreate = new UserRoleDto.Create();
			userRoleCreate.setRole("ROLE_USER");

			userRoleList.add(userRoleCreate);

			userCreate.setUserRoleList(userRoleList);

			try {
				//데이터가 없는경우에 save 한다
				if(userDAO.findByEmail(userCreate.getEmail()) == null) {
					this.mockMvc.perform(post("/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(userCreate)))
						// 처리 내용을 출력합니다.
						.andDo(print())
						.andExpect(status().isOk());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

