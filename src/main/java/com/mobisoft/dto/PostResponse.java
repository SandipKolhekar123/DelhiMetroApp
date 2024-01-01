package com.mobisoft.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostResponse {

	private List<PostDto> content; 
	
	private Integer pageNumber;
	
	private Integer pageSize;

	private Long totalElements;
	
	private Integer totalSize;
	
	private Boolean lastPage;
	
}
