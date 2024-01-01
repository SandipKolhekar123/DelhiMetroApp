package com.mobisoft.services;

import java.util.List;
import com.mobisoft.dto.PostDto;
import com.mobisoft.dto.PostResponse;

public interface PostServiceI {
	
	//create
	PostDto createPost(PostDto postDto, Long userId, Long categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Long postId);
		
	//delete
	void deletePost(Long postId);	
	
	//get single post by post id
	PostDto getPostById(Long postId);
	
	//get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
		
	//get all posts by category id
	List<PostDto> getPostsByCategory(Long categoryId);
	
	//get all posts by user id
	List<PostDto> getPostsByUser(Long userId);
	
	//search posts
	List<PostDto> searchPostsByTitle(String keyword);
	
}
