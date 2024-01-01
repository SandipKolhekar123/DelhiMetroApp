package com.mobisoft.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.dto.ApiResponse;
import com.mobisoft.dto.CommentDto;
import com.mobisoft.services.CommentServiceI;

@RestController
@RequestMapping("/api")
public class CommentController {
	/**
	 * @implNote The LoggerFactory is a utility class producing Loggers forvarious logging APIs, 
	 *           most notably for log4j, logback and JDK 1.4 logging. All methods in LoggerFactory are static.
	 */

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentServiceI commentServiceI;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId){
		logger.info("createComment method excecution started...");
		logger.info("createComment for a post with id: {}",postId);
		CommentDto comment = this.commentServiceI.createComment(commentDto, postId);
		logger.info("createComment method excecution ended...");
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
		logger.info("deleteComment method excecution started...");
		this.commentServiceI.deleteComment(commentId);
		logger.info("deleteComment of id: {}",commentId);
		logger.info("deleteComment method excecution ended...");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
	}
}
