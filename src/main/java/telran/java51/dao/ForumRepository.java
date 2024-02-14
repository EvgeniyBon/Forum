package telran.java51.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface ForumRepository extends CrudRepository<Post, String> {
	Stream<Post> getByAuthor(String author);
	Stream<Post>getPostsByTagsInAllIgnoringCase(List<String> tags);
	Stream<Post>getPostsByDateCreatedBetween(LocalDate from, LocalDate to);
}
