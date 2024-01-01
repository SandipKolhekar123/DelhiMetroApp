package com.mobisoft.services;

import com.mobisoft.dto.CommentDto;

public interface CommentServiceI {

	CommentDto createComment(CommentDto commentDto, Long postId);
	
	void deleteComment(Long commentId);
}
