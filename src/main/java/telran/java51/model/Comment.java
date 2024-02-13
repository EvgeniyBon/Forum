package telran.java51.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder

public class Comment {
	String user;
	@Setter
	String message;
	LocalDateTime dateCreated;
	@Setter
	long likes;

}
