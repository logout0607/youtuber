package com.todaywork.security.service.impl;

import com.todaywork.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 7. 22..
 */
@Repository("securityUserDAO")
public interface SecurityUserDAO  extends PagingAndSortingRepository<User, UUID> {

	List<User> findByEmailAndEnabled(String email, boolean enable);
}
