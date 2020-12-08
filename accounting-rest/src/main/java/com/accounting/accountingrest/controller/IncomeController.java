package com.accounting.accountingrest.controller;
import com.accounting.accountingrest.request.IncomeRequest;
import com.accounting.accountingrest.response.IncomeResponse;
import com.accounting.accountingrest.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public IncomeResponse getIncome(@PathVariable String id){
        return incomeService.findIncome(Integer.parseInt(id));
    }

    @PostMapping
    ResponseEntity<HttpStatus> createIncome(@RequestBody IncomeRequest incomeRequest){
        incomeService.createIncome(incomeRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteIncome(@PathVariable String id) throws Exception {
        int idNum = Integer.parseInt(id);
        incomeService.deleteIncome(idNum);
    }
}
