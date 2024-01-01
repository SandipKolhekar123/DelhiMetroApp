package com.mobisoft.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobisoft.dto.CategoryDto;
import com.mobisoft.services.CategoryServiceI;
		
		
@SpringBootTest(classes = CategoryControllerTest.class)
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.mobisoft")
class CategoryControllerTest {

	@Mock
	private CategoryServiceI categoryServiceI;
	
	@InjectMocks
	private CategoryController categoryController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void createCategorytTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		when(categoryServiceI.createCategory(categoryDto)).thenReturn(categoryDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String categoryAsString = mapper.writeValueAsString(categoryDto);
		
		mockMvc.perform(post("/api/categories/")
				.content(categoryAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	public void updateCategorytTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		when(categoryServiceI.updateCategory(categoryDto, categoryDto.getCategoryId())).thenReturn(categoryDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String categoryAsString = mapper.writeValueAsString(categoryDto);
		
		mockMvc.perform(put("/api/categories/{id}", categoryDto.getCategoryId())
				.content(categoryAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void getCategoryByIdTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		when(categoryServiceI.getCategory( categoryDto.getCategoryId())).thenReturn(categoryDto);
		
		mockMvc.perform(get("/api/categories/{id}", categoryDto.getCategoryId()))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".categoryId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath(".categoryTitle").value("Sport"))
			.andExpect(MockMvcResultMatchers.jsonPath(".categoryDescription").value("this category is for Sports"))
			.andDo(print());
	}
	
	@Test
	public void getCategoriesTest() throws Exception {
		List<CategoryDto> categoryDtos = new ArrayList<>();
		categoryDtos.add(new CategoryDto((long) 1, "Sport", "this category is for Sports"));
		categoryDtos.add(new CategoryDto((long) 2, "Economy", "this category is for Economy"));
		categoryDtos.add(new CategoryDto((long) 3, "IT", "this category is for IT"));
		
		when(categoryServiceI.getAllCategory()).thenReturn(categoryDtos);
		
		mockMvc.perform(get("/api/categories/"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void deleteCategoryTest() {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		int actualStatusCode = categoryController.deleteCategory(categoryDto.getCategoryId()).getStatusCode().value();

		int expectedStatusCode = 200;
		
		assertEquals(expectedStatusCode, actualStatusCode);

	}

}
