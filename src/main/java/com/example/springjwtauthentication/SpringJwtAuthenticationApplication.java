package com.example.springjwtauthentication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableSwagger2
//@EntityScan(basePackages = {"com.example.springjwtauthentication.course", "com.example.springjwtauthentication.student"})
@OpenAPIDefinition
public class SpringJwtAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtAuthenticationApplication.class, args);
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Security App")
                        .description("jwt based authentication")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("github link")
                        .url("https://github.com/farooq-maker-2/SpringJwtAuthentication"));
    }

}
