package com.mobisoft.config;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		
		//swagger docs customization 
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(getInfo())
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
	
	/**
	 * title 
	 * description
	 * version 
	 * termsOfServiceUrl
	 * contactName 
	 * licensetext
	 * licenseUrl
	 */

	private ApiInfo getInfo() {
		
		return new ApiInfo(
				"Blogging Application : Back-end", 
				"This project is developed for mobicool software solution pvt. ltd.",
				"1.0",
				"Terms of Service", 
				new Contact("Sandip Kolhekar", "http://www.mobicoolsoftwaresolutions.com/", "sandipkolhekar427@gmail.com"), 
				"Licence of APIs",
				"APIs URL",
				Collections.emptyList());
	}
}
