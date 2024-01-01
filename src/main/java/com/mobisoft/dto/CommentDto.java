package com.mobisoft.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CommentDto {

	private Long commentId;
	
	private String comment;
	
}
