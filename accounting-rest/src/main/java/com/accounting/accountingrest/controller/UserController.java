package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.LoginRequest;
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return userService.findUser(Integer.parseInt(id));
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PostMapping("/login/{id}")
    UserResponse login(@RequestBody LoginRequest loginRequest, @PathVariable String id) {
        return userService.login(loginRequest, Integer.parseInt(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateUser(@RequestBody UserRequest user, @PathVariable String id) {
        int idNum = Integer.parseInt(id);
        userService.updateUser(user, idNum);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable String id) {
        int idNum = Integer.parseInt(id);
        userService.deleteUser(idNum);
    }
}
