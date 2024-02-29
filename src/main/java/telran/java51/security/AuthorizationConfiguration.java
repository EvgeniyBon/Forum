package telran.java51.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.model.Role;


@Configuration
@RequiredArgsConstructor
public class AuthorizationConfiguration {
	final CustomWebSecurity customWebSecurity;

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize-> authorize
				.requestMatchers("/account/register","/forum/posts/**")
					.permitAll()
					.requestMatchers("/account/user/{login}/role/{role}")
					.hasRole(Role.ADMINISTRATOR.name())
					.requestMatchers(HttpMethod.PUT,"/account/user/{login}")
					.access(new WebExpressionAuthorizationManager("#login == authentication.name"))
					.requestMatchers(HttpMethod.DELETE,"/account/user/{login}")
					.access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))
					.requestMatchers(HttpMethod.POST,"/forum/post/{author}")
					.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
					.requestMatchers(HttpMethod.PUT,"/forum/post/{id}/comment/{author}")
					.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
					.requestMatchers(HttpMethod.PUT,"/forum/post/{id}")
					.access((auth,context)->{
						return new AuthorizationDecision(customWebSecurity.checkPostAuthor(auth.get(),context.getVariables().get("id")));
						})
					.requestMatchers(HttpMethod.DELETE,"/forum/post/{id}")
					.access((auth,context)->{
						return new AuthorizationDecision(customWebSecurity.checkDeletePostPermisson(auth.get(),context.getVariables().get("id")));
						})
				.anyRequest()
					.authenticated()
				);
		return http.build();
	}
}
