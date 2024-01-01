package com.mobisoft.services;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.mobisoft.dto.CategoryDto;

public interface CategoryServiceI {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	//delete
	void deleteCategory(Long categoryId);
	
	//get
	CategoryDto getCategory(Long categoryId);
	
	//get All
	List<CategoryDto> getAllCategory();
	
}
