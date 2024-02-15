package telran.java51.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
	public Iterable<PostDto> getPostsByAuthor(String author) {
		return forumRepository.getByAuthor(author).map(s->mapper.map(s, PostDto.class)).toList();
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
	public Iterable<PostDto> findPostByTags(List<String> tags) {
		return forumRepository.getPostsByTagsInAllIgnoringCase(tags).map(s -> mapper.map(s, PostDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public Iterable<PostDto> findPostByPeriod(PostPeriodDto periodDto) {
		return forumRepository.getPostsByDateCreatedBetween(periodDto.getDateFrom(), periodDto.getDateTo()).map(s->mapper.map(s, PostDto.class)).toList();
	}

	@Override
	public PostDto updatePost(String id, PostUpdateDto postUpdateDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (postUpdateDto.getTitle() != null) {
			post.setTitle(postUpdateDto.getTitle());
		}
		if (postUpdateDto.getTags() != null) {
			post.getTags().addAll(postUpdateDto.getTags());
		}
		if (postUpdateDto.getContent() != null) {
			post.setContent(postUpdateDto.getContent());
		}
		forumRepository.save(post);
		return mapper.map(post, PostDto.class);
	}
}
