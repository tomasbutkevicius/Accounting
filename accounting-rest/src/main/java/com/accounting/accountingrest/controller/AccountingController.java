package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.service.AccountingSystemService;
import com.accounting.accountingrest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class AccountingController {
    private final AccountingSystemService accountingSystemService;
    private final CategoryService categoryService;

    @Autowired
    public AccountingController(AccountingSystemService accountingSystemService, CategoryService categoryService){
        this.accountingSystemService = accountingSystemService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<AccountingSystemResponse> getAllAccountingSystems(){
        return accountingSystemService.findAll();
    }

    @GetMapping("/{id}/categories")
    public List<CategoryResponse> getSystemCategories(@PathVariable String id){
        return categoryService.getCategoriesInSystem(id);
    }

    @GetMapping("/{id}")
    public AccountingSystemResponse getAccountingSystem(@PathVariable String id){
        return accountingSystemService.findAccountingSystem(Integer.parseInt(id));
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

    @DeleteMapping(value = "/{id}")
    public void deleteAccountingSystem(@PathVariable String id){
        int idNum = Integer.parseInt(id);
        accountingSystemService.deleteAccountingSystem(idNum);
    }

}
