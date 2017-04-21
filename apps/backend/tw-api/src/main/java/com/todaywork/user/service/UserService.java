package com.todaywork.user.service;

import com.todaywork.dto.UserDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by 권 오빈 on 2016. 6. 7..
 */
@Service("userService")
public interface UserService {
	/**
	 * 유저등록 서비스
	 * @param userCreate
	 * @throws Exception
	 */
	UserDto.Response insertUser(UserDto.Create userCreate) throws Exception;

	/**
	 * 유저목록불러오기 서비스
	 * @param pageable
	 * @throws Exception
	 */
	PageImpl<UserDto.Response> findUserAll(Pageable pageable);

	/**
	 * 유저하나선택 서비스
	 * @param email
	 * @throws Exception
	 */
	UserDto.Response findUserOne(String email);

	/**
	 * 유저수정 서비스
	 * @param userUpdate
	 * @throws Exception
	 */
	UserDto.Response updateUser(UserDto.Update userUpdate) throws Exception;

	/**
	 * 유저 완전 삭제 서비스
	 * @param userDelete
	 */
	void deleteUser(UserDto.Delete userDelete);

	/**
	 * 유저 enabled 수정 서비스
	 * @param userUpdate
	 */
	void updateEnabled(UserDto.Update userUpdate);

	/**
	 * 유저 검색 서비스 (이메일, 이름, enalbed)로 할수있다
	 * @param userSearch
	 * @return
	 */
	PageImpl<UserDto.Response> findUserSearch(UserDto.Search userSearch, Pageable pageable);
}
