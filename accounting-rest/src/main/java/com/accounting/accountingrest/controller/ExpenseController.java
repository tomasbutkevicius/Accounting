package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.ExpenseRequest;
import com.accounting.accountingrest.response.ExpenseResponse;
import com.accounting.accountingrest.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseResponse> getAllExpenses(){
        return expenseService.findAll();
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpense(@PathVariable String id){
        return expenseService.findExpense(Integer.parseInt(id));
    }

    @PostMapping
    ResponseEntity<HttpStatus> createExpense(@RequestBody ExpenseRequest expenseRequest){
        expenseService.createExpense(expenseRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteExpense(@PathVariable String id) throws Exception {
        int idNum = Integer.parseInt(id);
        expenseService.deleteExpense(idNum);
    }
}
