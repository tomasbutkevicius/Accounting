package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Expense;
import com.accounting.accountingrest.hibernate.repository.*;
import com.accounting.accountingrest.hibernate.service.ExpenseServiceHib;
import com.accounting.accountingrest.request.ExpenseRequest;
import com.accounting.accountingrest.response.ExpenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");

    @Autowired
    public ExpenseService() {
    }

    public List<ExpenseResponse> findAll() {
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        List<Expense> expenses = expenseHibController.getExpenseList();
        List<ExpenseResponse> responseList = new ArrayList<>();

        for (Expense expense : expenses) {
            responseList.add(new ExpenseResponse(expense));
        }
        return responseList;
    }

    public void createExpense(ExpenseRequest expenseRequest) {
        if(expenseRequest.getName() == null || expenseRequest.getAmount() == null || expenseRequest.getCategoryID() == 0 )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(expenseRequest.getCategoryID());
        if(category == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
        Expense expense = new Expense(expenseRequest.getName(), expenseRequest.getAmount(), LocalDate.now());
        expense.setCategory(category);

        ExpenseServiceHib.create(entityManagerFactory, category.getAccountingSystem(), expense, category);
    }

    public void deleteExpense(int id) throws Exception {
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        Expense expense = expenseHibController.getById(id);
        if(expense == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found");
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        ExpenseServiceHib.delete(entityManagerFactory, accountingSystemHib.getById(expense.getCategory().getAccountingSystem().getId()),
                expense.getCategory(), expense);
    }

    public ExpenseResponse findExpense(int id) {
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        Expense expense = expenseHibController.getById(id);
        if(expense == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found");
        }
        return new ExpenseResponse(expense);
    }
}
