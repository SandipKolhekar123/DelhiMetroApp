package com.mobisoft.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mobisoft.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
