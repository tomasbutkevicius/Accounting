package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.response.IncomeResponse;
import com.accounting.accountingrest.service.CategoryService;
import com.accounting.accountingrest.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService){
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<IncomeResponse> getAllIncomes(){
        return incomeService.findAll();
    }
}
