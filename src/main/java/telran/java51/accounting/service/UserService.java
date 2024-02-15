package telran.java51.accounting.service;

import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.UserUpdateDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.dto.UserRoleDto;

public interface UserService {
	UserDto userRegister(NewUserDto newUserDto);

	UserDto deleteUser(String login);

	UserDto userUpdate(String login, UserUpdateDto userUpdateDto);

	UserRoleDto addRole(String login, String role);

	UserRoleDto deleteRole(String login, String role);

	UserDto getUser(String id);
}
