//package com.example.springjwtauthentication.security;
//
//import com.example.springjwtauthentication.filter.TeacherAuthenticationFilter;
//import com.example.springjwtauthentication.filter.TeacherAuthorizatonFilter;
//import com.example.springjwtauthentication.teacher.api.TeacherService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
//@Order(100)
//public class TeacherSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final TeacherService teacherService;
//
//    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(teacherService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //http.authorizeRequests().anyRequest().permitAll();
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        TeacherAuthenticationFilter teacherAuthenticationFilter = new TeacherAuthenticationFilter(authenticationManagerBean());
//        teacherAuthenticationFilter.setFilterProcessesUrl("/api/teacher/login/**");
//        http.requestMatchers().antMatchers("/api/teacher/**");
////       http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
//                .antMatchers("/api/teacher/login/**").permitAll()
//                //.antMatchers("/api/student/**")
//                //.permitAll()
//                .antMatchers("/api/teacher/**")
//                //.permitAll()
//                //.anyRequest()
//                .authenticated();
//
//        http.addFilter(/*new CustomAuthenticationFilter(authenticationManagerBean())*/teacherAuthenticationFilter);
//        http.addFilterBefore(new TeacherAuthorizatonFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/teacher/register");
//    }
//
//
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//}
