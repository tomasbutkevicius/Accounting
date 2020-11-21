package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.service.AccountingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class AccountingController {
    private final AccountingSystemService accountingSystemService;

    @Autowired
    public AccountingController(AccountingSystemService accountingSystemService){
        this.accountingSystemService = accountingSystemService;
    }

//    @RequestMapping(value="/systems",method= RequestMethod.GET)
    @GetMapping
    public List<AccountingSystemResponse> getAllAccountingSystems(){
        return accountingSystemService.findAll();
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody AccountingSystemRequest accountingSystem){
        accountingSystemService.createAccountingSystem(accountingSystem);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }
}
