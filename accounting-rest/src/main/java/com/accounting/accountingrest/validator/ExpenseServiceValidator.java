package com.accounting.accountingrest.validator;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Expense;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.ExpenseHibController;
import com.accounting.accountingrest.request.ExpenseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpenseServiceValidator {
    private ExpenseHibController expenseHibController;
    private CategoryHibController categoryHibController;

    public ExpenseServiceValidator(ExpenseHibController expenseHibController, CategoryHibController categoryHibController) {
        this.expenseHibController = expenseHibController;
        this.categoryHibController = categoryHibController;
    }

    public void validateFind(int id){
        Expense expense = expenseHibController.getById(id);
        if(expense == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found");
        }
    }

    public void validateCreate(ExpenseRequest expenseRequest){
        if(expenseRequest.getName() == null || expenseRequest.getAmount() == null || expenseRequest.getCategoryID() == 0 )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        Category category = categoryHibController.getById(expenseRequest.getCategoryID());
        if(category == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
    }

}
