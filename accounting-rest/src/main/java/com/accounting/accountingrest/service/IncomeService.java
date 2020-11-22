package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Income;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.IncomeHibController;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.hibernate.service.IncomeServiceHib;
import com.accounting.accountingrest.request.IncomeRequest;
import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.response.IncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");

    @Autowired
    public IncomeService() {
    }

    public List<IncomeResponse> findAll() {
        IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
        List<Income> incomes = incomeHibController.getIncomeList();
        List<IncomeResponse> responseList = new ArrayList<>();

        for (Income income : incomes) {
            responseList.add(new IncomeResponse(income));
        }
        return responseList;
    }

    public void createIncome(IncomeRequest incomeRequest) {
        if(incomeRequest.getName() == null || incomeRequest.getAmount() == null || incomeRequest.getCategoryID() == 0 )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(incomeRequest.getCategoryID());
        if(category == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
        Income income = new Income(incomeRequest.getName(), incomeRequest.getAmount());
        income.setCategory(category);

        IncomeServiceHib.create(entityManagerFactory, category.getAccountingSystem(), income, category);
    }

    public void deleteIncome(int id) throws Exception {
        IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
        Income income = incomeHibController.getById(id);
        if(income == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Income not found");
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        IncomeServiceHib.delete(entityManagerFactory, accountingSystemHib.getById(income.getCategory().getAccountingSystem().getId()),
                income.getCategory(), income);
    }

}
