package com.mobisoft.security;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.reflect.CatchClauseSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mobisoft.exceptions.RequestRejectedExceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
 
	private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, RuntimeException {
	
//		1. get token
		String requestToken = request.getHeader("Authorization");
		
//	 	Bearer 23435656hkdsds
		System.out.println(requestToken);
		
		String username = null, token  = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);
			
			try {
				
				username = this.jwtTokenHelper.getUserNameFromToken(token);
				
			}catch(IllegalArgumentException e){
				
				System.out.println("Unable to get JWT token!");
				
			}catch(ExpiredJwtException e) {
				
				System.out.println("JWT token has expired!");
				
			}catch(MalformedJwtException e) {
				
				System.out.println("Invalid JWT.It was not correctly constructed!");
			}
			
		}else {
			System.out.println("Jwt token does not begin with Bearer");
		}
		
//		2.Once we get the token, now validate and then set Authentication
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				//do authentication 
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {
				
				System.out.println("Invalid JWT token");
			}
		}else {
			System.out.println("Username is null or Context is not null");
		}
//	 	3.perform filter
			
			filterChain.doFilter(request, response); 
		
    }
}