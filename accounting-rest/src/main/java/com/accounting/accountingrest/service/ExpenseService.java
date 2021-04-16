package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Expense;
import com.accounting.accountingrest.hibernate.repository.*;
import com.accounting.accountingrest.hibernate.service.ExpenseServiceHib;
import com.accounting.accountingrest.request.ExpenseRequest;
import com.accounting.accountingrest.response.ExpenseResponse;
import com.accounting.accountingrest.validator.ExpenseServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
    private ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
    private CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
    ExpenseServiceValidator expenseServiceValidator = new ExpenseServiceValidator(expenseHibController, categoryHibController);
    @Autowired
    public ExpenseService() {
    }

    public List<ExpenseResponse> findAll() {
        List<Expense> expenses = expenseHibController.getExpenseList();
        List<ExpenseResponse> responseList = new ArrayList<>();

        for (Expense expense : expenses) {
            responseList.add(new ExpenseResponse(expense));
        }
        return responseList;
    }

    public void createExpense(ExpenseRequest expenseRequest) {
        expenseServiceValidator.validateCreate(expenseRequest);
        Category category = categoryHibController.getById(expenseRequest.getCategoryID());
        Expense expense = new Expense(expenseRequest.getName(), expenseRequest.getAmount(), LocalDate.now());
        expense.setCategory(category);

        ExpenseServiceHib.create(entityManagerFactory, category.getAccountingSystem(), expense, category);
    }

    public void deleteExpense(int id) throws Exception {
        expenseServiceValidator.validateFind(id);
        Expense expense = expenseHibController.getById(id);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        ExpenseServiceHib.delete(entityManagerFactory, accountingSystemHib.getById(expense.getCategory().getAccountingSystem().getId()),
                expense.getCategory(), expense);
    }

    public ExpenseResponse findExpense(int id) {
        expenseServiceValidator.validateFind(id);
        Expense expense = expenseHibController.getById(id);
        return new ExpenseResponse(expense);
    }
}
