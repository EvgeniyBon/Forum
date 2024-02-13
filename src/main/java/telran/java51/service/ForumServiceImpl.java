package telran.java51.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.dao.ForumRepository;
import telran.java51.dto.CommentDto;
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
	public Post addPost(Post post) {
		forumRepository.save(post);
		return post;
	}

	@Override
	public Optional<Post> getPostById(String id) {
		return forumRepository.findById(id);
	}

	@Override

	public boolean addLike(String id) {
		Optional<Post> post = forumRepository.findById(id);
		return post.map(p -> {
			p.setLikes(p.getLikes() + 1);
			forumRepository.save(p);
			return true;
		}).orElse(false);
	}

	@Override
	public List<Post> getPostsByAuthor(String author) {
		return forumRepository.getByAuthor(author);
	}

	@Override
	public Optional<Post> addComment(String id, CommentDto commentDto) {
		Optional<Post> post = forumRepository.findById(id);
		return Optional.ofNullable(post.map(p -> {
			Comment comment = Comment.builder().user(commentDto.getUser()).likes(0).message(commentDto.getMessage())
					.dateCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)).build();
			p.getComents().add(comment);
			return forumRepository.save(p);
		}).orElse(null));
	}

	@Override
	public Optional<Post> deletePostById(String id) {
		Optional<Post> post = forumRepository.findById(id);
		post.ifPresent(p -> forumRepository.delete(p));
		return post;
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
	public Optional<Post> updatePost(String id, PostUpdateDto postUpdateDto) {
		Optional<Post> post = forumRepository.findById(id);
		return Optional.ofNullable(post.map(p -> {
			p.setTitle(postUpdateDto.getTitle());
			p.getTags().addAll(postUpdateDto.getTags());
			p.setContent(postUpdateDto.getContent());
			forumRepository.save(p);
			return p;
		}).orElse(null));
	}
}
