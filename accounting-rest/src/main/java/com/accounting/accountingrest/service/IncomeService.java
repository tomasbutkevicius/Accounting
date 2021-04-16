package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Income;
import com.accounting.accountingrest.hibernate.repository.*;
import com.accounting.accountingrest.hibernate.service.IncomeServiceHib;
import com.accounting.accountingrest.request.IncomeRequest;
import com.accounting.accountingrest.response.IncomeResponse;
import com.accounting.accountingrest.validator.IncomeServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
    private IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
    private CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
    IncomeServiceValidator incomeServiceValidator = new IncomeServiceValidator(incomeHibController, categoryHibController);

    @Autowired
    public IncomeService() {
    }

    public List<IncomeResponse> findAll() {
        List<Income> incomes = incomeHibController.getIncomeList();
        List<IncomeResponse> responseList = new ArrayList<>();

        for (Income income : incomes) {
            responseList.add(new IncomeResponse(income));
        }
        return responseList;
    }

    public void createIncome(IncomeRequest incomeRequest) {
        incomeServiceValidator.validateCreate(incomeRequest);
        Income income = new Income(incomeRequest.getName(), incomeRequest.getAmount(), LocalDate.now());
        Category category = categoryHibController.getById(incomeRequest.getCategoryID());
        income.setCategory(category);

        IncomeServiceHib.create(entityManagerFactory, category.getAccountingSystem(), income, category);
    }

    public void deleteIncome(int id) throws Exception {
        incomeServiceValidator.validateFind(id);
        Income income = incomeHibController.getById(id);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        IncomeServiceHib.delete(entityManagerFactory, accountingSystemHib.getById(income.getCategory().getAccountingSystem().getId()),
                income.getCategory(), income);
    }

    public IncomeResponse findIncome(int id) {
        incomeServiceValidator.validateFind(id);
        return new IncomeResponse(incomeHibController.getById(id));
    }
}
