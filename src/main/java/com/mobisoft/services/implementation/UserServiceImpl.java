package com.mobisoft.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobisoft.config.AppConstants;
import com.mobisoft.dto.UserDto;
import com.mobisoft.entities.Role;
import com.mobisoft.entities.User;
import com.mobisoft.exceptions.ResourceNotFoundException;
import com.mobisoft.repositories.RoleRepo;
import com.mobisoft.repositories.UserRepo;
import com.mobisoft.services.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI{
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//RegisterNewUser
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.ROLE_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepository.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	//create 
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = userRepository.save(user);
		return this.userToDto(savedUser);
	}
	
	//update
	
	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		User user = this.userRepository.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepository.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}
	
	//get User

	@Override
	public UserDto getUserById(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		return this.userToDto(user);
	}
	
	//get All Users

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	//delete User
	
	@Override
	public void deleteUser(Long userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		this.userRepository.delete(user);
	}
	
//User-UserDto mapping
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}
}
