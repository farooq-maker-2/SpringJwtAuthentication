package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.view.request.LoginCreds;
import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.view.response.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final StudentService studentService;

    @RolesAllowed({"TEACHER", "STUDENT"})
    @Operation(summary = "this api is to register the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PostMapping(path = "/users/register", produces = "application/json", consumes = "application/json")
    public HttpResponse<Boolean> registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }


    @PostMapping(path = "/users/login", produces = "application/json", consumes = "application/json")
    @Operation(summary = "this api is to login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    public HttpResponse<UserView> UserLogin(@RequestBody LoginCreds loginCreds) {

        return userService.loginUser(loginCreds);
    }

    @RolesAllowed({"TEACHER", "STUDENT"})
    @Operation(summary = "this api is to deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @DeleteMapping(path = "/users/{userId}/deactivate/{role}", produces = "application/json")
    public HttpResponse<Boolean> deactivateUser(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("userId") Long userId,
                                                @PathVariable("role") String role) {

        return studentService.optOutAndDeleteStudent(userId);
    }
}
