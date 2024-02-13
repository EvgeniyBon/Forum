package telran.java51.dto;

import java.util.List;

import lombok.Getter;
@Getter
public class NewPostDto {
	String title;
	String content;
	List<String>tags;
}
