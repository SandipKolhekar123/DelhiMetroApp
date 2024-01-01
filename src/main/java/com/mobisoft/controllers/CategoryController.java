package com.mobisoft.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.dto.ApiResponse;
import com.mobisoft.dto.CategoryDto;
import com.mobisoft.services.CategoryServiceI;
import com.mobisoft.services.implementation.CategoryServiceImpl;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	 
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	// this is category controller - dev
	@Autowired
	private CategoryServiceI categoryServiceI;
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote create new category
	 * @param categoryId
	 * @return the saved category
	 * *************************************
	 */
	 
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		logger.info("createCategory method execution stated...");
		CategoryDto createCategory = this.categoryServiceI.createCategory(categoryDto);
		logger.info("createCategory {}",createCategory);
		logger.info("createCategory method execution ended with response...");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote update existing category
	 * @param categoryDto
	 * @param categoryId
	 * @return the updated category
	 * *************************************
	 */
	
	@PutMapping("/{categotyId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categotyId){
		logger.info("updateCategory method execution stated...");
		CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categotyId);
		logger.info("updateCategory {}",updateCategory);
		logger.info("updateCategory method execution ended with response...");
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote delete existing category record
	 * @param categoryId
	 * @return message for delete category
	 * *************************************
	 */
	
	@DeleteMapping("/{categotyId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categotyId){
		logger.info("deleteCategory method execution stated...");
		this.categoryServiceI.deleteCategory(categotyId);
		logger.info("deleteCategory with id: {}",categotyId);
		logger.info("deleteCategory method execution ended...");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully!", true), HttpStatus.OK);
	}

	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote get the specified category record that matches with categoryId
	 * @param categoryId
	 * @return the single category record
	 * ***********************************
	 */
	
	@GetMapping("/{categotyId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categotyId){
		logger.info("getCategory method execution stated...");
		CategoryDto categoryDto = this.categoryServiceI.getCategory(categotyId);
		logger.info("getCategory with id: {}", categotyId);
		logger.info("getCategory method execution ended with response...");
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	/** ***********************************
	 * @author Sandip Kolhekar
	 * @return the all existing categories
	 * ***********************************
	 */
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		logger.info("getCategories method execution stated...");
		List<CategoryDto> categoryDtos = this.categoryServiceI.getAllCategory() ;
		logger.info("getCategories method execution ended with response...");
		return ResponseEntity.ok(categoryDtos);
	}
}
