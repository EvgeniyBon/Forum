package telran.java51.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import telran.java51.model.Comment;

@Getter
public class PostDto {
	String id;
	String title;
	String content;
	@Setter
	String author;
	LocalDateTime dateCreated;
	Set<String> tags;
	long likes;
	List<Comment> coments;
}
