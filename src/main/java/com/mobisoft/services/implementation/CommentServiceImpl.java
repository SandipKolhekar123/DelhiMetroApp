package com.mobisoft.services.implementation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.dto.CommentDto;
import com.mobisoft.entities.Comment;
import com.mobisoft.entities.Post;
import com.mobisoft.exceptions.ResourceNotFoundException;
import com.mobisoft.repositories.CommentRepo;
import com.mobisoft.repositories.PostRepo;
import com.mobisoft.services.CommentServiceI;

@Service
public class CommentServiceImpl implements CommentServiceI{

	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//create comment 
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	//delete comment
	
	@Override
	public void deleteComment(Long commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		this.commentRepo.delete(comment);
	}

}
