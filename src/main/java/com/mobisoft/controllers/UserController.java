package com.mobisoft.controllers;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobisoft.dto.ApiResponse;
import com.mobisoft.dto.UserDto;
import com.mobisoft.services.UserServiceI;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceI userServiceI;

	/** ************************************
	 * @author Sandip Kolhekar(ID)
	 * @apiNote create new user
	 * @param userDto
	 * @return the saved user
	 * *************************************
	 */

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDto2 = this.userServiceI.createUser(userDto);
		return new ResponseEntity<UserDto>(userDto2, HttpStatus.CREATED);		
	}
	
	/** ************************************
	 * @author Sandip Kolhekar(ID)
	 * @apiNote update existing user record
	 * @param userDto
	 * @param uid
	 * @return the updated user
	 * *************************************
	 */
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Long uid){
		UserDto updateUser = userServiceI.updateUser(userDto, uid);
		return  ResponseEntity.ok(updateUser);    // static ok() method -> return ok().body(body);
	}
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote delete existing user only by -> ADMIN
	 * @param userId
	 * @return message for deletion of record
	 * *************************************
	 */
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){   //use '?' if we don't know return type
		this.userServiceI.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully.", true), HttpStatus.OK);
		//return new ResponseEntity(Map.of("message","User Deleted Successfully"), HttpStatus.OK);
	}

	/** ***********************************
	 * @author Sandip Kolhekar(ID)
	 * @return the all existing user records
	 * ***********************************
	 */
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userServiceI.getAllUsers());
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote fetch the specified user record that match with userId
	 * @param userId
	 * @return the single user record
	 * ***********************************
	 */
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
		return ResponseEntity.ok(this.userServiceI.getUserById(userId));
	}
	
}
