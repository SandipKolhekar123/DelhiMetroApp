package com.mobisoft.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mobisoft.entities.Category;
import com.mobisoft.entities.Post;
import com.mobisoft.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
