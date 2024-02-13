package telran.java51.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import telran.java51.dao.ForumRepository;
import telran.java51.dto.CommentDto;
import telran.java51.dto.NewPostDto;
import telran.java51.dto.PostDto;
import telran.java51.dto.PostPeriodDto;
import telran.java51.dto.PostUpdateDto;
import telran.java51.model.Comment;
import telran.java51.model.Post;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumSerivce {
	final ForumRepository forumRepository;
	final ModelMapper mapper;

	@Override
	public PostDto addPost(String author, NewPostDto newPostDto) {
		Post post = mapper.map(newPostDto, Post.class);
		post.setAuthor(author);
		forumRepository.save(post);
		return mapper.map(post, PostDto.class);
	}

	@Override
	public PostDto getPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return mapper.map(post, PostDto.class);
	}

	@Override

	public void addLike(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		post.setLikes(post.getLikes() + 1);
		forumRepository.save(post);
	}

	@Override
	public List<Post> getPostsByAuthor(String author) {
		return forumRepository.getByAuthor(author);
	}

	@Override
	public PostDto addComment(String id, String user, CommentDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Comment comment = Comment.builder().user(user).likes(0).message(commentDto.getMessage()).build();
		post.getComents().add(comment);
		forumRepository.save(post);
		return mapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id = " + id + " not found"));
		forumRepository.delete(post);
		return mapper.map(post, PostDto.class);
	}

	@Override
	public List<Post> findPostByTags(List<String> tags) {
		return forumRepository.getPostsByTagsInAllIgnoringCase(tags);
	}

	@Override
	public List<Post> findPostByPeriod(PostPeriodDto periodDto) {
		return forumRepository.getPostsByDateCreatedBetween(periodDto.getDateFrom(), periodDto.getDateTo());
	}

	@Override
	public PostDto updatePost(String id, PostUpdateDto postUpdateDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		post.setTitle(postUpdateDto.getTitle());
		post.getTags().addAll(postUpdateDto.getTags());
		post.setContent(postUpdateDto.getContent());
		forumRepository.save(post);
		return mapper.map(post, PostDto.class);
	}
}
