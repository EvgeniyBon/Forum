package telran.java51.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder

public class Comment {
	String user;
	@Setter
	String message;
	@Default
	LocalDateTime dateCreated=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	@Setter
	long likes;

}
