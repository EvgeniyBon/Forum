package telran.java51.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.dto.CommentDto;
import telran.java51.dto.NewPostDto;
import telran.java51.dto.PostDto;
import telran.java51.dto.PostPeriodDto;
import telran.java51.dto.PostUpdateDto;
import telran.java51.model.Post;
import telran.java51.service.ForumServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {
	final ForumServiceImpl forumServiceImpl;
	final ModelMapper mapper;

	@PostMapping("/post/{user}")
	PostDto addPost(@RequestBody NewPostDto newPostDto, @PathVariable String user) {
		return forumServiceImpl.addPost(user, newPostDto);
	}

	@GetMapping("/post/{id}")
	PostDto getPost(@PathVariable String id) {
		return forumServiceImpl.getPostById(id);
	}

	@PutMapping("/post/{id}/like")
	ResponseEntity<Void> addLike(@PathVariable String id) {
		forumServiceImpl.addLike(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/posts/author/{author}")
	List<Post> getPostByAuthor(@PathVariable String author) {
		return forumServiceImpl.getPostsByAuthor(author);
	}

	@PutMapping("/post/{id}/comment/{user}")
	PostDto addComent(@RequestBody CommentDto commentDto, @PathVariable String id, @PathVariable String user) {
		return forumServiceImpl.addComment(id, user, commentDto);
	}

	@DeleteMapping("/post/{id}")
	PostDto deletePost(@PathVariable String id) {
		return forumServiceImpl.deletePostById(id);
	}

	@PostMapping("/posts/tags")
	List<Post> getPostByTags(@RequestBody List<String> tags) {
		return forumServiceImpl.findPostByTags(tags);
	}

	@PostMapping("/posts/period")
	List<Post> getPostByPeriod(@RequestBody PostPeriodDto periodDto) {
		return forumServiceImpl.findPostByPeriod(periodDto);
	}

	@PutMapping("/post/{id}")
	PostDto updatePost(@RequestBody PostUpdateDto postUpdateDto, @PathVariable String id) {
		return forumServiceImpl.updatePost(id, postUpdateDto);
	}
}
