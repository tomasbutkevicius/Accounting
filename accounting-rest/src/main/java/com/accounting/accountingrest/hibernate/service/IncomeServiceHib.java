package com.accounting.accountingrest.hibernate.service;


import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.IncomeHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Income;

import javax.persistence.EntityManagerFactory;

public class IncomeServiceHib {
    public static void create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Income income, Category category) {
        accountingSystem.addIncome(income.getAmount());
        category.getIncomes().add(income);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
    }

    public static boolean delete(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category, Income income) throws Exception {
        accountingSystem.decreaseIncome(income.getAmount());
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        categoryHibController.removeIncomeFromCategory(category, income);
        IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
        incomeHibController.delete(income.getId());

        return true;
    }
}
