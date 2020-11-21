package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.service.AccountingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountingController {
    private final AccountingSystemService accountingSystemService;

    @Autowired
    public AccountingController(AccountingSystemService accountingSystemService){
        this.accountingSystemService = accountingSystemService;
    }

    @RequestMapping(value="/systems",method= RequestMethod.GET)
    public List<AccountingSystemResponse> getAllAccountingSystems(){
        return accountingSystemService.findAll();
    }
}
