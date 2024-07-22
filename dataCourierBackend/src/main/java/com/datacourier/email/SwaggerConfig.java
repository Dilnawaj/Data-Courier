package com.datacourier.email;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {
	
	
	@Bean
	  public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("SpringShop API")
	              .description("Spring shop sample application")
	              .version("1.0")
	              .license(new License().name("Dilnawaj").url("http://springdoc.org")))
	              .externalDocs(new ExternalDocumentation()
	              .description("SpringShop Wiki Documentation")
	              .url("https://springshop.wiki.github.org/docs"));
	  }
	
	
	
	
}
