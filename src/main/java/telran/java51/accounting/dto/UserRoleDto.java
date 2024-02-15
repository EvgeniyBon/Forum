package telran.java51.accounting.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class UserRoleDto {
	String login;
	Set<String> roles;
}
