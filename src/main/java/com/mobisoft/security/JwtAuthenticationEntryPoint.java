package com.mobisoft.security;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	/**
	 * @author Sandip Kolhekar
	 * @apiNote start of authentication here
	 */
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
	/**
	 * Sends an error response to the client using the specified status code and clears the output buffer.
	 */
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied!");
		
	    
	}
}
