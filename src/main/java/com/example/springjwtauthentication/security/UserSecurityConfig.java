package com.example.springjwtauthentication.security;

import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.security.filter.UserAuthenticationFilter;
import com.example.springjwtauthentication.security.filter.UserAuthorizatonFilter;
import com.example.springjwtauthentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(101)
//to enable preAuthorize annotation on endpoints for role based authentication
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * By default a newly created CorsConfiguration does not permit
         * any cross-origin requests and must be configured explicitly
         * to indicate what should be allowed. Use applyPermitDefaultValues()
         * to flip the initialization model to start with open defaults that
         * permit all cross-origin requests for GET, HEAD, and POST requests.
         */

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));

        //allow customs headers for cors request
        /**
         * By default a newly created CorsConfiguration does not permit
         * any cross-origin requests and must be configured explicitly
         * to indicate what should be allowed. Use applyPermitDefaultValues()
         * to flip the initialization model to start with open defaults that
         * permit all cross-origin requests for GET, HEAD, and POST requests.
         */
        http.cors().
//      configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).
        configurationSource(request -> corsConfig).
                and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(authenticationManagerBean());
        userAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
        http.authorizeRequests()
                //.antMatchers("/api/admins/**").hasRole("ADMIN")
                .antMatchers("/api/users/login/**").permitAll()
                .antMatchers("api/courses").permitAll()
                .antMatchers("/api/users/register").permitAll()
        /*.antMatchers("/api/courses/**").hasRole(ApplicationUserRole.STUDENT.name().toLowerCase())*/;

        http.
                authorizeRequests().
                antMatchers("/api/students/{studentId}/**").
                access("@userSecurity.hasUserId(authentication,#studentId)");
        //access(hasUserId(authentication,studentId));

//        http.
//                authorizeRequests().
//                antMatchers("/api/teachers/{teacherId}/**").
//                access("@userSecurity.hasUserId(authentication,#teacherId)");

        http.requestMatchers().antMatchers("/api/**");
        http.requestMatchers().antMatchers("/api/**");
        http.authorizeRequests().antMatchers("/api/users/**").authenticated();
        //http.headers().

        http.addFilter(userAuthenticationFilter);
        http.addFilterBefore(new UserAuthorizatonFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Component("userSecurity")
    public class UserSecurity {
        public boolean hasUserId(Authentication authentication, Long userId) {
            // do your check(s) here
            //boolean isSameUser = false;
            //User user = (User) authentication.getCredentials();
            //System.out.println("inside security function");
            if (Long.valueOf(authentication.getPrincipal().toString()) == userId) {

                return true;
            } else {
                UserModel user = userService.findUserById(Long.valueOf(authentication.getPrincipal().toString()));
                if (user.getRole().equals("admin")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
