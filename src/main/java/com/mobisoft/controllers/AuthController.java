package com.mobisoft.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobisoft.dto.JwtAuthRequest;
import com.mobisoft.dto.JwtAuthResponse;
import com.mobisoft.dto.UserDto;
import com.mobisoft.exceptions.InvalidUserAndPasswordException;
import com.mobisoft.security.JwtTokenHelper;
import com.mobisoft.services.UserServiceI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	//THIS IS AUTH
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
	
		logger.debug("createToken method excecution started...");
		
		this.authenticate(request.getUsername(), request.getPassword());
		 
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		 
		String token = this.jwtTokenHelper.generateToken(userDetails);
			
		JwtAuthResponse response = new JwtAuthResponse();
			
		response.setToken(token);
			
		logger.debug("createToken method excecution ended...");
			
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception{
		
		logger.debug("user authentication stated...");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {	
			this.authenticationManager.authenticate(authenticationToken);
			
			logger.debug("user authentication completed...");
			
		}catch(BadCredentialsException e) {
			logger.debug("Catch BadCredentialsException...");
			System.err.println("Invalid login Details...!");
			throw new InvalidUserAndPasswordException(username); //custom checked exception
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
		UserDto registeredUser = userServiceI.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
}
