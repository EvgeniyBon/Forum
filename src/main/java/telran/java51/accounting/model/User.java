package telran.java51.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Document(collection = "users")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "login")
public class User {
	@Id
	String login;
	String password;
	@Setter
	String firstName;
	@Setter
	String lastName;
	Set<String> roles;

	public User() {
		roles = new HashSet<>();
		addRole("USER");
	}

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}
}
