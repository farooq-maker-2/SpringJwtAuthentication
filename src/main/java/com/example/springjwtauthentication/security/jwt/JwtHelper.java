package com.example.springjwtauthentication.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtHelper {

    public static void generateJwtCode(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 260 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("Access-Control-Expose-Headers", "access_token, refresh_token");
    }

    public static void verifyJwtCode(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String authorizationHeader = request.getHeader("AUTHORIZATION");
        System.out.println(request.getParameter("page"));
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            try {
                String jwt_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(jwt_token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception exception) {
                exceptionHandler(exception, response);
            }
        } else {
            exceptionHandler(new Exception(), response);
        }

    }

    public static Long getJwtUser(String token) {

        if (token != null && token.startsWith("Bearer ")) {

            try {
                String jwt_token = token.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(jwt_token);
                return Long.valueOf(decodedJWT.getSubject());

            } catch (Exception exception) {
                throw new ResourceAccessException("resource not allowed");
            }
        } else {
            throw new ResourceAccessException("resource not allowed");
        }
    }

    public static void exceptionHandler(Exception exception, HttpServletResponse response) {
        log.error("Error logging in : {} ", exception.getMessage());
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        //response.sendError(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_messege", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
