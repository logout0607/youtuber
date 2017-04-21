package com.todaywork.user;

import com.todaywork.dto.UserDto;
import com.todaywork.user.service.UserService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 박 제민  on 2016. 6. 24..
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource(name = "userService")
	private UserService userService;

	@GetMapping(value = "/find-all")
	public PageImpl<UserDto.Response> findUserAll(@PageableDefault(value = 20) Pageable pageable) {
		return userService.findUserAll(pageable);
	}

	@GetMapping(value = "")
	public UserDto.Response findUserOne(@RequestParam("email") String email) {
		return userService.findUserOne(email);
	}

	@PostMapping("/save")
	public UserDto.Response insertUser(
		@RequestBody  UserDto.Create userCreate
	) throws Exception {
		return userService.insertUser(userCreate);
	}

	@PatchMapping("/save")
	public UserDto.Response updateUser(
		@RequestBody  UserDto.Update userUpdate
	) throws Exception {
		return userService.updateUser(userUpdate);
	}

	@PostMapping("/delete")
	public void deleteUser(
		@RequestBody  UserDto.Delete userDelete
	) throws Exception {
		userService.deleteUser(userDelete);
	}

	@PatchMapping("/update-enabled")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEnabled(
		@RequestBody UserDto.Update userUpdate
	) throws Exception {
		userService.updateEnabled(userUpdate);
	}

	@PostMapping("/search")
	public PageImpl<UserDto.Response> findUserSearch(@RequestBody UserDto.Search userSearch, @PageableDefault(value = 20, sort = "lastModifiedDate", direction = Sort.Direction.ASC) Pageable pageable){
		return userService.findUserSearch(userSearch, pageable);
	}

}
