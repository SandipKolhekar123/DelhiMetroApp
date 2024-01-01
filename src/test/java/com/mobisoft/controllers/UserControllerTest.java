package com.mobisoft.controllers;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobisoft.dto.UserDto;
import com.mobisoft.services.implementation.UserServiceImpl;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@SpringBootTest(classes = UserControllerTest.class)
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.mobisoft")
class UserControllerTest {

	@Mock
	private UserServiceImpl userServiceImpl;
	
	@InjectMocks
	private UserController userController;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void createUserTest() throws Exception {
	
	
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		
		when(userServiceImpl.createUser(userDto)).thenReturn(userDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String userAsString = mapper.writeValueAsString(userDto);
		
		mockMvc.perform(post("/api/users/")
				.content(userAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	public void updateUserTest() throws Exception {

	 UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
	 Long userId = 1l;
		
		when(userServiceImpl.updateUser(userDto,userId)).thenReturn(userDto);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String userAsString = mapper.writeValueAsString(userDto);
		
		mockMvc.perform(put(("/api/users/{id}"), userId)
				.content(userAsString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void getUserByIdTest() throws Exception {
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		Long userId = 1l;
		
		when(userServiceImpl.getUserById(userId)).thenReturn(userDto);
		
		mockMvc.perform(get("/api/users/{id}", userId))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath(".name").value("sandy"))  // .andExpect(jsonPath("$.name", is(userDto.getName())))
			.andExpect(MockMvcResultMatchers.jsonPath(".email").value("sandy@gmail.com"))
			.andExpect(MockMvcResultMatchers.jsonPath(".password").value("Sandy123@gmail.com"))
			.andExpect(MockMvcResultMatchers.jsonPath(".about").value("QA"))
			.andDo(print());
	}
	//negative 
//	@Test
//	public void getUserById404Test() throws Exception {
//		ApiResponse apiResponse = new ApiResponse();
//		Long userId = 1l;
//		
//		when(userServiceImpl.getUserById(userId)).thenReturn(Optional.empty());
//		
//		mockMvc.perform(get("/api/users/{id}", userId))
//			.andExpect(status().isNotFound())
//			.andDo(print());
//	}
	
	@Test
	public void getAllUsersTest() throws Exception {
		List<UserDto> userDtos = new ArrayList<>();
		userDtos.add(new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null));
		userDtos.add(new UserDto((long) 2, "jack", "jack@gmail.com", "Jack123@gmail.com", "QA", null));
		userDtos.add(new UserDto((long) 3, "rock", "rock@gmail.com", "Rock123@gmail.com", "QA", null));
	
		when(userServiceImpl.getAllUsers()).thenReturn(userDtos);
		
		mockMvc.perform(get("/api/users/"))
			.andExpect(jsonPath("$.size()", is(userDtos.size())))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		
		UserDto userDto = new UserDto((long) 1, "sandy", "sandy@gmail.com", "Sandy123@gmail.com", "QA", null);
		
		int actualStatusCode = userController.deleteUser(userDto.getId()).getStatusCode().value();
	 
	 	int expectedStatusCode = 200;
	 
	 	assertEquals(expectedStatusCode, actualStatusCode);
	}
	
}