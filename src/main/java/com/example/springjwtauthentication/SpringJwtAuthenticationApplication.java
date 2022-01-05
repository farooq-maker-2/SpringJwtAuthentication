package com.example.springjwtauthentication;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootApplication
@EnableSwagger2
//@EntityScan(basePackages = {"com.example.springjwtauthentication.course", "com.example.springjwtauthentication.student"})
public class SpringJwtAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtAuthenticationApplication.class, args);
    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                //paths to look at by swagger
                .paths(PathSelectors.ant("/api/**"))
                //package to look at by swagger
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey("Access Token", AUTHORIZATION, SecurityScheme.In.HEADER.name());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("unlimited", "description")};
        return singletonList(new SecurityReference("unlimited", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "School Management System",
                "This is a basic CRUD application with Spring security",
                "1.0",
                "http://localhost:8081/api/",
                "API License",
                "http://localhost:8081/api/", null
        );
        return apiInfo;
    }

}
