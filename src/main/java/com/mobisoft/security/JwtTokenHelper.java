package com.mobisoft.security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Sandip Kolhekar
 * @apiNote JwtTokenHelper.class nothin but JwtTokenUtils.class useful to extract token components info
 */

@Component
public class JwtTokenHelper {
 
	/**
	 * don't specify constants in the class
	 * either specify in .properties file or in case of micreservices store it in config server
	 */
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private String secrete = "jwtTokenKey";
	
	//retrieve username from jwt token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
		final Claims claims = getAllClaimsFromToken(token);
		return  claimsResolver.apply(claims);
	}
	
	//for retrieving any information from token we will need the secrete key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secrete).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	//while creating the token -
	//1. Define claims of the token, like Issuer, Expiration, Subject and the ID
	//2. Sign the JWT using the HSS12 algorithm and secrete key.
	//3. According to JWS Compact Serialization(http://tools.ietf.org/html/draft-ietf-jose)
	//4. compaction of the JWT to  the URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				  .setIssuedAt(new Date(System.currentTimeMillis()))
				  .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				  .signWith(SignatureAlgorithm.HS512, secrete)
				  .compact();
	}
	
	
	//validate token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
