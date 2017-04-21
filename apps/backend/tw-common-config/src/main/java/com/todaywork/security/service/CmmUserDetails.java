package com.todaywork.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by 권 오빈 on 2016. 1. 26.
 */
public class CmmUserDetails extends User {

	public CmmUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CmmUserDetails(String username, String password, boolean enabled,
	                      boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
	                      Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public CmmUserDetails(String username, String password, boolean enabled,
						  boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						  Collection<? extends GrantedAuthority> authorities, Object userEntity) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setUser(userEntity);
	}

	private Object user;

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	//@Override
	public String getUserId() {
		return ((com.todaywork.domain.User) getUser()).getUniqueId().toString();
	}
}
