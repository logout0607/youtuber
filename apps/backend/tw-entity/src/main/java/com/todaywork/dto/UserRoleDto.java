package com.todaywork.dto;

import lombok.Data;

/**
 * Created by 권 오빈 on 2016. 6. 7..
 */
public class UserRoleDto {

	@Data
	public static class Create {
		private String role;
	}

	@Data
	public static class Response {
		private String role;
	}
}
