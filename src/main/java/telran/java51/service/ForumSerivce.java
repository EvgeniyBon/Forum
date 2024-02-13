package telran.java51.service;

import java.util.List;

import telran.java51.dto.CommentDto;
import telran.java51.dto.NewPostDto;
import telran.java51.dto.PostDto;
import telran.java51.dto.PostPeriodDto;
import telran.java51.dto.PostUpdateDto;
import telran.java51.model.Post;

public interface ForumSerivce {
	PostDto addPost(String author, NewPostDto newPostDto);

	PostDto getPostById(String id);

	void addLike(String id);

	List<Post> getPostsByAuthor(String author);

	PostDto addComment(String id, String user, CommentDto commentDto);

	PostDto deletePostById(String id);

	List<Post> findPostByTags(List<String> tags);

	List<Post> findPostByPeriod(PostPeriodDto periodDto);

	PostDto updatePost(String id, PostUpdateDto postUpdateDto);

}
