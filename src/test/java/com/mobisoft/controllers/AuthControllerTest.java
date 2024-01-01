package com.mobisoft.controllers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mobisoft.dto.JwtAuthRequest;
import com.mobisoft.security.JwtTokenHelper;
import com.mobisoft.services.implementation.UserServiceImpl;


@SpringBootTest(classes = AuthControllerTest.class)
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.mobisoft")
public class AuthControllerTest {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtAuthRequest request;
	
	@Autowired
	private UserDetails userDetails;
	
	@Mock
	private UserServiceImpl userServiceImpl;
	
	@InjectMocks
	private AuthController authController;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
		request.setUsername("sandip");
		request.setPassword("sandy");
    }
	
	@Test
	public void createTokenTest() {
		Mockito.when(this.userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
		
	}

}
