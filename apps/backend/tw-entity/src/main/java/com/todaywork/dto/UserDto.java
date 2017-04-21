package com.todaywork.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 6. 7..
 */
public class UserDto {
	@Data
	public static class Create {
		private String email;
		private String password;
		private String userName;

		private List<UserRoleDto.Create> userRoleList;
	}

	@Data
	public static class Response {
		private UUID uniqueId;
		private String email;
		private String userName;
		private boolean enabled;
		private DateTime createdDate;
		private DateTime lastModifiedDate;

		private List<UserRoleDto.Response> userRoleList;
	}

	@Data
	public static class Delete {
		private UUID uniqueId;
	}

	@Data
	public static class Update {
		private UUID uniqueId;
		private String password;
		private String userName;
		private boolean enabled;
		private List<UserRoleDto.Create> userRoleList;
	}

	@Data
	public static class Search {
		private String email;
		private String userName;
		private String enabled;
	}

	@Data
	public static class Session {
		private UUID uniqueId;
		private String email;
		private String userName;
		private long expires;
		private List<UserRoleDto.Response> userRoleList;
	}

}
