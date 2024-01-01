package com.mobisoft.controllers;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobisoft.dto.CategoryDto;
import com.mobisoft.dto.PostDto;
import com.mobisoft.dto.PostResponse;
import com.mobisoft.dto.UserDto;
import com.mobisoft.services.PostServiceI;

@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.mobisoft")
@SpringBootTest(classes = PostControllerTest.class)
class PostControllerTest {

	@Mock
	private PostServiceI postServiceI;
	
	@InjectMocks
	private PostController postController;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		
		 mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
	}

	@Test
	public void createPostTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		
		PostDto postDto = new PostDto((long) 1, "post on sport article", "this is post description", "default.png", null, categoryDto, userDto, null);
		
		when(postServiceI.createPost(postDto, (long)1, (long)1)).thenReturn(postDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String postValueAsString = mapper.writeValueAsString(postDto);
		
		mockMvc.perform(post("/api/user/{userId}/category/{catId}/posts",userDto.getId(), categoryDto.getCategoryId()).
				content(postValueAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
			}
	
	@Test
	public void updatePostTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		
		PostDto postDto = new PostDto((long) 1, "post on sport article", "this is post description", "default.png", null, categoryDto, userDto, null);
		
		when(postServiceI.updatePost(postDto, (long)1)).thenReturn(postDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String postValueAsString = mapper.writeValueAsString(postDto);
		
		mockMvc.perform(put("/api/posts/{postId}",postDto.getId()).
				content(postValueAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void getAllPostsTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto((long) 1, "Sport", "this category is for Sports");
		
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		
		List<PostDto> postDtos = new ArrayList<>();
		postDtos.add(new PostDto((long) 1, "post on sport article", "this is post description", "default.png", null, categoryDto, userDto, null));
		postDtos.add(new PostDto((long) 1, "post on environment article", "this is environment post description", "default.png", null, categoryDto, userDto, null));
		postDtos.add(new PostDto((long) 1, "post on economy article", "this is economy post description", "default.png", null, categoryDto, userDto, null));
		Integer pageNumber = 0;
		Integer pageSize = 2;
		Long totalElements = 15l;
		Integer totalSize = 5;
		Boolean lastPage = false;

		PostResponse postResponse = new PostResponse(postDtos, pageNumber, pageSize, totalElements, totalSize, lastPage);
		
		when(postServiceI.getAllPost(pageNumber, pageSize, null, null)).thenReturn(postResponse);
		
		mockMvc.perform(get("/api/posts/"))
		.andExpect(status().isOk())
		.andDo(print());
	}
}
