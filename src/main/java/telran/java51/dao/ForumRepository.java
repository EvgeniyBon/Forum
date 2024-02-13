package telran.java51.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.model.Post;

import java.time.LocalDate;
import java.util.List;

public interface ForumRepository extends CrudRepository<Post, String> {
	List<Post> getByAuthor(String author);
	List<Post>getPostsByTagsInAllIgnoringCase(List<String> tags);
	List<Post>getPostsByDateCreatedBetween(LocalDate from, LocalDate to);
}
