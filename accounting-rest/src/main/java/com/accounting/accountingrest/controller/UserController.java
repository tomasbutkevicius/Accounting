package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.UserRequest;
import com.accounting.accountingrest.response.UserResponse;
import com.accounting.accountingrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping
    public List<UserResponse> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }
}
