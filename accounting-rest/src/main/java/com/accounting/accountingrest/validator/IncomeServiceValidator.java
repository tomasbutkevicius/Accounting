package com.accounting.accountingrest.validator;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Income;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.IncomeHibController;

import com.accounting.accountingrest.request.IncomeRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncomeServiceValidator {
    private IncomeHibController incomeHibController;
    private CategoryHibController categoryHibController;

    public IncomeServiceValidator(IncomeHibController incomeHibController, CategoryHibController categoryHibController) {
        this.incomeHibController = incomeHibController;
        this.categoryHibController = categoryHibController;
    }

    public void validateFind(int id){
        Income income = incomeHibController.getById(id);
        if(income == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Income not found");
        }
    }

    public void validateCreate(IncomeRequest incomeRequest){
        if(incomeRequest.getName() == null || incomeRequest.getAmount() == null || incomeRequest.getCategoryID() == 0 )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        Category category = categoryHibController.getById(incomeRequest.getCategoryID());
        if(category == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
    }

}
