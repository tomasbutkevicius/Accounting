package service;

import controller.AccountingSystemController;
import controller.CategoryController;
import model.AccountingSystem;
import model.Category;
import model.User;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CategoryService {
    public static String create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category) {
        category.setAccountingSystem(accountingSystem);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);

        categoryHibController.create(category);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
        return "action complete";
    }

    public static String createSubCategory(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category parentCategory, Category category, User activeUser) {
        return "to be released";
    }

    public static List<Category> getAllCategoriesInSystem(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        return categoryHibController.getAllCategoriesInSystem(accountingSystem);
    }
}
