package com.accounting.accountingrest.hibernate.service;


import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CategoryServiceHib {
    public static String create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category) {
        category.setAccountingSystem(accountingSystem);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);

        categoryHibController.create(category);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
        return "category create action complete";
    }

    public static String createSubCategory(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category) {
        category.setAccountingSystem(accountingSystem);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);

        categoryHibController.create(category);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
        return "subcategory create action complete";
    }

    public static List<Category> getAllCategoriesInSystem(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        return categoryHibController.getAllCategoriesInSystem(accountingSystem);
    }

    public static void addResponsibleUser(Category selectedCategory, User responsibleUser, EntityManagerFactory entityManagerFactory) throws Exception {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        categoryHibController.addUserToCategory(selectedCategory.getId(), responsibleUser.getId());
    }

}
