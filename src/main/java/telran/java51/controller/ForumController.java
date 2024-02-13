package telran.java51.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import telran.java51.dto.CommentDto;
import telran.java51.dto.NewPostDto;
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
	ResponseEntity<Post> addPost(@RequestBody NewPostDto newPostDto, @PathVariable String user) {
		Post post = mapper.map(newPostDto, Post.class);
		post.setAuthor(user);
		forumServiceImpl.addPost(post);
		return ResponseEntity.ok(post);
	}

	@GetMapping("/post/{id}")
	ResponseEntity<Post> getPost(@PathVariable String id) {
		Optional<Post> post = forumServiceImpl.getPostById(id);
		return post.map(p -> ResponseEntity.ok(p)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/post/{id}/like")
	ResponseEntity<Void> addLike(@PathVariable String id) {
		if (forumServiceImpl.addLike(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/posts/author/{author}")
	ResponseEntity<List<Post>> getPostByAuthor(@PathVariable String author) {
		return ResponseEntity.ok(forumServiceImpl.getPostsByAuthor(author));
	}

	@PutMapping("/post/{id}/comment/{user}")
	ResponseEntity<Post> addComent(@RequestBody CommentDto comentDto, @PathVariable String id,
			@PathVariable String user) {
		comentDto.setUser(user);
		Optional<Post> response = forumServiceImpl.addComment(id, comentDto);
		return response.map(r -> ResponseEntity.ok(r))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/post/{id}")
	ResponseEntity<Post> deletePost(@PathVariable String id) {
		Optional<Post> post = forumServiceImpl.deletePostById(id);
		return post.map(p -> ResponseEntity.ok(p)).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id = " + id + " not found"));
	}

	@PostMapping("/posts/tags")
	ResponseEntity<List<Post>> getPostByTags(@RequestBody List<String> tags) {
		return ResponseEntity.ok(forumServiceImpl.findPostByTags(tags));
	}

	@PostMapping("/posts/period")
	ResponseEntity<List<Post>> getPostByPeriod(@RequestBody PostPeriodDto periodDto) {
		List<Post> posts = forumServiceImpl.findPostByPeriod(periodDto);
		return ResponseEntity.ok(posts);
	}

	@PutMapping("/post/{id}")
	ResponseEntity<Post> updatePost(@RequestBody PostUpdateDto postUpdateDto, @PathVariable String id) {
		Optional<Post> updatedPost = forumServiceImpl.updatePost(id, postUpdateDto);
		return updatedPost.map(p -> ResponseEntity.ok(updatedPost.get())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
