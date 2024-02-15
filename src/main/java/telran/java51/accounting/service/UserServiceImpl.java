package telran.java51.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dao.UserRepository;
import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.dto.UserRoleDto;
import telran.java51.accounting.dto.UserUpdateDto;
import telran.java51.accounting.dto.exceptions.UserDtoException;
import telran.java51.accounting.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ModelMapper mapper;

	@Override
	public UserDto userRegister(NewUserDto newUserDto) {
		if (!userRepository.existsById(newUserDto.getLogin())) {
			User newUser = mapper.map(newUserDto, User.class);
			userRepository.save(newUser);
			return mapper.map(newUser, UserDto.class);
		}
		throw new UserDtoException("User " + newUserDto.getLogin() + " already exists...");
	}

	@Override
	public UserDto deleteUser(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserDtoException("User not found"));
		userRepository.delete(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto userUpdate(String login, UserUpdateDto userUpdateDto) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserDtoException("User not found"));
		if (userUpdateDto.getFirstName() != null) {
			user.setFirstName(userUpdateDto.getFirstName());
		}
		if (userUpdateDto.getLastName() != null) {
			user.setLastName(userUpdateDto.getLastName());
		}
		userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserRoleDto addRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserDtoException("User not found"));
		if (role != null) {
			user.addRole(role);
			userRepository.save(user);
		}
		return mapper.map(user, UserRoleDto.class);
	}

	@Override
	public UserRoleDto deleteRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserDtoException("User not found"));
		if (role != null) {
			user.removeRole(role);
			userRepository.save(user);
		}
		return mapper.map(user, UserRoleDto.class);
	}

	@Override
	public UserDto getUser(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return mapper.map(user, UserDto.class);

	}

}
