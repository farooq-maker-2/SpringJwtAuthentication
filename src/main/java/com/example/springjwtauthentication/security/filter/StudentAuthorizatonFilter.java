//package com.example.springjwtauthentication.filter;
//
//import com.example.springjwtauthentication.jwt.JwtHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//public class StudentAuthorizatonFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        if (request.getServletPath().equals("/api/student/login") || request.getServletPath().equals("/api/student/register")) {
//            filterChain.doFilter(request, response);
//        } else {
//            JwtHelper.verifyJwtCode(request, response, filterChain);
//        }
//    }
//}
