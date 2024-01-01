package com.mobisoft.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mobisoft.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>{

}
