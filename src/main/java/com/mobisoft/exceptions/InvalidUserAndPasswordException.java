package com.mobisoft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidUserAndPasswordException extends Exception {
	String fieldValue;

	public InvalidUserAndPasswordException(String fieldValue) {
		super(String.format("Invalid password for username : %s",fieldValue));
		this.fieldValue = fieldValue;
	}
	
	
}
