package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.request.UserRequest;
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

    @GetMapping
    public List<AccountingSystemResponse> getAllAccountingSystems(){
        return accountingSystemService.findAll();
    }

    @PostMapping
    ResponseEntity<HttpStatus> createAccountingSystem(@RequestBody AccountingSystemRequest accountingSystemRequest){
        accountingSystemService.createAccountingSystem(accountingSystemRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateAccountingSystem(@RequestBody AccountingSystemRequest accountingSystemRequest, @PathVariable String id){
        int idNum = Integer.parseInt(id);
        accountingSystemService.updateAccountingSystem(accountingSystemRequest, idNum);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }
}
