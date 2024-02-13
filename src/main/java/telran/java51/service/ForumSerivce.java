package telran.java51.service;

import java.util.List;
import java.util.Optional;

import telran.java51.dto.CommentDto;
import telran.java51.dto.PostPeriodDto;
import telran.java51.dto.PostUpdateDto;
import telran.java51.model.Post;

public interface ForumSerivce {
	Post addPost(Post post);

	Optional<Post> getPostById(String id);

	boolean addLike(String id);

	List<Post> getPostsByAuthor(String author);

	Optional<Post> addComment(String id, CommentDto commentDto);

	Optional<Post> deletePostById(String id);

	List<Post> findPostByTags(List<String> tags);

	List<Post> findPostByPeriod(PostPeriodDto periodDto);

	Optional<Post> updatePost(String id, PostUpdateDto postUpdateDto);

}
