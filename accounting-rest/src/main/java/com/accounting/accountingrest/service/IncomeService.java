package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Income;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.IncomeHibController;
import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.response.IncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
