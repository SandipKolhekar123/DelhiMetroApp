package com.mobisoft;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mobisoft.config.AppConstants;
import com.mobisoft.entities.Role;
import com.mobisoft.repositories.RoleRepo;

@SpringBootApplication
public class BlogsAppApplication implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(BlogsAppApplication.class);
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		logger.info("Logging started for the {}", BlogsAppApplication.class.getSimpleName());
		SpringApplication.run(BlogsAppApplication.class, args);
	}
	
	
  /**
   * ModelMapper - Performs object mapping, maintains Configuration and stores TypeMaps. 
   * 	•To perform object mapping use map.
   * 	•To configure the mapping of one type to another use createTypeMap.
   * 	•To add mappings for specific properties use addMappings supplying a PropertyMap.
   * 	•To configure ModelMapper use getConfiguration. 
   *    •To validate mappings use validate. 
   */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}	

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("samuel"));
		
		try {
			Role role1 = new Role();
			role1.setRole_id(AppConstants.ROLE_ADMIN);
			role1.setRole_name("ADMIN");
			
			Role role2 = new Role();
			role2.setRole_id(AppConstants.ROLE_STAFF);
			role2.setRole_name("STAFF");

			Role role3 = new Role();
			role3.setRole_id(AppConstants.ROLE_USER);
			role3.setRole_name("USER");
			
			List<Role> roles = List.of(role1, role2, role3);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(role->{
				System.out.println(role.getRole_name());
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
