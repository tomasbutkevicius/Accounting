package com.accounting.accountingrest.hibernate.service;

import com.accounting.accountingrest.hibernate.controller.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.controller.CategoryHibController;
import com.accounting.accountingrest.hibernate.controller.ExpenseHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Expense;

import javax.persistence.EntityManagerFactory;

public class ExpenseServiceHib {
    public static void create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Expense expense, Category category) {
        accountingSystem.addExpense(expense.getAmount());
        category.getExpenses().add(expense);
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        expenseHibController.create(expense);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
    }

    public static boolean delete(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category, Expense expense) throws Exception {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        categoryHibController.removeExpenseFromCategory(category, expense);
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        expenseHibController.delete(expense.getId());

        return true;
    }
}