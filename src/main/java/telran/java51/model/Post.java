package telran.java51.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@NoArgsConstructor
@Document(collection = "posts")
@EqualsAndHashCode(of = "id")
public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	LocalDateTime dateCreated =LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	Set<String> tags = new HashSet<>();
	@Setter
	long likes;
	List<Comment> coments = new ArrayList<>();
}
