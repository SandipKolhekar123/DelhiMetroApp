package com.mobisoft.controllers;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mobisoft.config.AppConstants;
import com.mobisoft.dto.PostDto;
import com.mobisoft.dto.PostResponse;
import com.mobisoft.services.FileServiceI;
import com.mobisoft.services.PostServiceI;

@RestController
@RequestMapping("/api")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private PostServiceI postServiceI;
	
	@Autowired
	private FileServiceI fileServiceI;
	
	@Value("${project.image}")
	private String path;
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote create new post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return the saved user
	 * *************************************
	 */
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid
			                                  @RequestBody PostDto postDto,
											  @PathVariable Long userId,
											  @PathVariable Long categoryId)
	{
		logger.info("createPost method execution started...");
		PostDto createdPost = this.postServiceI.createPost(postDto, userId, categoryId);
		logger.info("createPost for user with userId {}", userId," and categoryId {}", categoryId);
		logger.info("createPost method execution started...");
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote update existing post record
	 * @param postDto
	 * @param postId
	 * @return the updated post
	 * *************************************
	 */
	
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
		logger.info("updatePost method execution started...");
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);
		logger.info("updatePost with id: {}", postId);
		logger.info("updatePost method execution ended...");
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}
	
	/** ************************************
	 * @author Sandip Kolhekar
	 * @apiNote delete existing post record
	 * @param postId
	 * @return message after deleting post
	 * *************************************
	 */
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePostById(@PathVariable Long postId){
		logger.info("deletePostById method execution started...");
		this.postServiceI.deletePost(postId);
		logger.info("deletePost for Id: {}", postId);
		logger.info("deletePost method execution ended...");
		return new ResponseEntity<String>("Post deleted successfully", HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote fetch the specified post that match with postId
	 * @param postId
	 * @return the single post
	 * ***********************************
	 */
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
		return ResponseEntity.ok(this.postServiceI.getPostById(postId));
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @return the all existing posts
	 * ***********************************
	 */
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
		PostResponse postResponse = this.postServiceI.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote get the posts for particular category
	 * @param categoryId
	 * @return the list of post
	 * ***********************************
	 */
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId){
		List<PostDto> posts = this.postServiceI.getPostsByCategory(categoryId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote get the posts for particular user
	 * @param userId
	 * @return the list of posts
	 * ***********************************
	 */
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
		List<PostDto> posts = this.postServiceI.getPostsByUser(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote search the posts with matched keyword
	 * @param keyword
	 * @return the list of posts
	 * ***********************************
	 */
	
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keyword) {
		List<PostDto> result = this.postServiceI.searchPostsByTitle(keyword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote upload post image and stored into the database
	 * @throws IOException 
	 * @param postId
	 * @param image
	 * @return the database updated post   
	 * ************************************
	 */

	@PostMapping("/posts/images/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, 
													 @PathVariable Long postId) throws IOException{
		PostDto postDto = this.postServiceI.getPostById(postId);
		String fileName = this.fileServiceI.uploadImage(path, image);
		//save image file into database
		postDto.setPostImage(fileName);
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}
	
	/** ***********************************
	 * @author Sandip Kolhekar
	 * @apiNote fetch image 
	 * @throws IOException 
	 * @param imageName
	 * @return the image   
	 * ************************************
	 */
	
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downLoadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		 
		InputStream resource = this.fileServiceI.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
