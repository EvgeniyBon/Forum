package telran.java51.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PostUpdateDto {
String title;
List<String> tags;
String content;
}
