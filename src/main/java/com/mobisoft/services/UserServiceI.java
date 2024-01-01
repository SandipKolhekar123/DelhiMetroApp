package com.mobisoft.services;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.mobisoft.dto.UserDto;

@Repository
public interface UserServiceI {

	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Long id);
	
	UserDto getUserById(Long id);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long id);
	
}
