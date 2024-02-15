package telran.java51.accounting.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.dto.UserRoleDto;
import telran.java51.accounting.dto.UserUpdateDto;
import telran.java51.accounting.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountingController {
	private final UserService userService;

	@PostMapping("/register")
	UserDto userRegister(@RequestBody NewUserDto newUserDto) {
		return userService.userRegister(newUserDto);
	}

	@DeleteMapping("/user/{login}")
	UserDto deleteUser(@PathVariable String login) {
		return userService.deleteUser(login);
	}

	@PutMapping("/user/{login}")
	UserDto userUpdate(@RequestBody UserUpdateDto userUpdateDto, @PathVariable String login) {
		return userService.userUpdate(login, userUpdateDto);
	}

	@PutMapping("/user/{login}/role/{role}")
	UserRoleDto addRole(@PathVariable String login, @PathVariable String role) {
		return userService.addRole(login, role);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	UserRoleDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return userService.deleteRole(login, role);
	}

	@GetMapping("user/{login}")
	UserDto getUser(@PathVariable String login) {
		return userService.getUser(login);
	}
}
