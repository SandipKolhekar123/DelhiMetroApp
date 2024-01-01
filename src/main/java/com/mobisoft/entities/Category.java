package com.mobisoft.entities;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "categories")
/**  
 * @implNote @Value Generates a lot of code like @Data and represent class as immutable entity.
 */
//@Value  
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	@Column(name = "cat_title", length = 100, nullable = false)
	private String categoryTitle;
	
	@Column(name = "cat_description")
	private String categoryDescription;
	
	/**
	 * @implNote CascadeType-Defines the set of cascadable operations that are propagated to the associated entity.The value cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
	 */
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private  List<Post> posts = new ArrayList<>();
}
