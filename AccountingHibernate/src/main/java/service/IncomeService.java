package service;

import controller.CategoryController;
import controller.IncomeController;
import model.AccountingSystem;
import model.Category;
import model.Income;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import persistenceController.IncomeHibController;

import javax.persistence.EntityManagerFactory;

public class IncomeService {
    public static void create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Income income, Category category) {
        accountingSystem.addIncome(income.getAmount());
        category.getIncomes().add(income);
        IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
        incomeHibController.create(income);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
    }

    public static boolean delete(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category, Income income) throws Exception {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        categoryHibController.removeIncomeFromCategory(category, income);
        IncomeHibController incomeHibController = new IncomeHibController(entityManagerFactory);
        incomeHibController.delete(income.getId());

        return true;
    }
}
