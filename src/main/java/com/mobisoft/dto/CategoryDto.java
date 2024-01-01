package com.mobisoft.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
	
	private Long categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Minimum size of title must be 4 letters!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "Minimum size of title must be 10 letters!")
	private String categoryDescription;
	
}
