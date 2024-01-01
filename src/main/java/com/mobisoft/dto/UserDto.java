package com.mobisoft.dto;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobisoft.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

	private Long id;
	
	@NotEmpty
	@Size(min = 4, max = 50,message = "Username must be atleast 4 characters !")   
	private String name;	//@NotEmpty - null as well as empty values
							//@NotNull - only detects null values but not empty values
//	@Email
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9+_-]+@[a-zA-Z0-9.]+$", message = "Please enter valid Email Id !")
	private String email;
	
	/**
	 * @implNote regex for password validation
	 * 	^ - start-of-string
	 * 	(?=.*[0-9]) - a digit must occur at least once
	 * 	(?=.*[a-z]) - a lower case letter must occur at least once
	 *  (?=.*[A-Z]) - an upper case letter must occur at least once
	 *  (?=.*[@#$%^&]) - a special character must occur at least once
     *	(?=\S+$) - no whitespace allowed in the entire string
	 * 	.{6,} - anything, at least six places
	 *	$ - end-of-string
	 */
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&])(?=\\S+$).{6,}$" , message = "Please enter valid password !")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();
}	
