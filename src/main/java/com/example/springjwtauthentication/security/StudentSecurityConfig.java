//package com.example.springjwtauthentication.security;
//
//import com.example.springjwtauthentication.filter.StudentAuthenticationFilter;
//import com.example.springjwtauthentication.filter.StudentAuthorizatonFilter;
//import com.example.springjwtauthentication.student.api.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Order(101)
//public class StudentSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    private final StudentService studentService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
////    }
//
////    @Autowired
////    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(studentService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable();
////        http.sessionManagement().sessionCreationPolicy(STATELESS);
////        //http.authorizeRequests().antMatchers("api/student/login").authenticated();
////        //http.authorizeRequests().anyRequest().permitAll();
////        http.addFilter(new StudentAuthenticationFilter(authenticationManagerBean()));
//
//
////        http.csrf().disable();
////        StudentAuthenticationFilter studentAuthenticationFilter = new StudentAuthenticationFilter(authenticationManagerBean());
////        studentAuthenticationFilter.setFilterProcessesUrl("/api/student/login");
////        http.sessionManagement().sessionCreationPolicy(STATELESS);
//////       http.authorizeRequests().anyRequest().permitAll();
////        http.addFilter(/*new CustomAuthenticationFilter(authenticationManagerBean())*/studentAuthenticationFilter);
//
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        StudentAuthenticationFilter studentAuthenticationFilter = new StudentAuthenticationFilter(authenticationManagerBean());
//        studentAuthenticationFilter.setFilterProcessesUrl("/api/student/login");
//        http.authorizeRequests()
//                .antMatchers("/api/student/login/**").permitAll()
//                .antMatchers("/api/student/register").permitAll();
//
//        http.requestMatchers().antMatchers("/api/student/**");
//        http.authorizeRequests()
//                //.antMatchers("/api/student/login/**").permitAll()
//                //.antMatchers("/api/student/register").permitAll()
//                //.antMatchers("/api/student/**")
//                //.permitAll()
//                .antMatchers("/api/student/**").authenticated();
//                //.permitAll()
//                //.anyRequest()
//                //.authenticated();
//
//        http.addFilter(studentAuthenticationFilter);
//        http.addFilterBefore(new StudentAuthorizatonFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//}
