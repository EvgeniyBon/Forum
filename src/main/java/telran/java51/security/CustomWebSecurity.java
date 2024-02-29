package telran.java51.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.model.Role;
import telran.java51.post.dao.PostRepository;
import telran.java51.post.model.Post;

@Service("customWebSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {
	final PostRepository postRepository;

	public boolean checkPostAuthor(Authentication authentication, String postId) {
		boolean access = false;
		Post post = postRepository.findById(postId).orElse(null);
		String userName = authentication.getName();
		if (post != null) {
			access = post.getAuthor().equals(userName);
		}
		return access;
	}

	public boolean checkDeletePostPermisson(Authentication authentication, String postId) {
		boolean isModerator = authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_"+Role.MODERATOR.name()));
		return isModerator || checkPostAuthor(authentication, postId);
	}
}
