package com.todaywork.user.service.impl;

import com.todaywork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 1. 26
 */
@Repository("userDAO")
public interface UserDAO extends PagingAndSortingRepository<User, UUID> {
	/**
	 * 이메일로 유저 1개 가져오기
	 * @param email
	 * @return 이메일에 맞는 User
	 */
	User findByEmail(String email);

	/**
	 * 	enabled를 업데이트 시켜준다
	 * @param uniqueId
	 * @param enabled
	 */
	@Modifying
	@Query("update User a set a.enabled = ?2  where a.uniqueId = ?1")
	int updateEnabled(UUID uniqueId, boolean enabled);

	Page<User> findByEmailIgnoreCaseContainingAndEnabled(String likeExpression, boolean enabled, Pageable pageable);

	Page<User> findByUserNameIgnoreCaseContainingAndEnabled(String likeExpression, boolean enabled, Pageable pageable);

	Page<User> findByEnabled(boolean enabled, Pageable pageable);

	Page<User> findByUserNameIgnoreCaseContaining(String likeExpression, Pageable pageable);

	Page<User> findByEmailIgnoreCaseContaining(String likeExpression, Pageable pageable);
}
