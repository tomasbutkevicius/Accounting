package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.response.UserResponse;
import com.accounting.accountingrest.service.AccountingSystemService;
import com.accounting.accountingrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value="/users",method= RequestMethod.GET)
    public List<UserResponse> getAllUsers(){
        return userService.findAll();
    }
}
