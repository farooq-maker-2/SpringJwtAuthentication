package com.example.springjwtauthentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * By default a newly created CorsConfiguration does not permit
     * any cross-origin requests and must be configured explicitly
     * to indicate what should be allowed. Use applyPermitDefaultValues()
     * to flip the initialization model to start with open defaults that
     * permit all cross-origin requests for GET, HEAD, and POST requests.
     */

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        return corsConfig;
    }
}