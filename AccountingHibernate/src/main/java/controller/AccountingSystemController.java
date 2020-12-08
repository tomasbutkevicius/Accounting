package controller;


import model.*;
import persistenceController.ExpenseHibController;
import persistenceController.IncomeHibController;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AccountingSystemController {

    public static Boolean removeCategory(AccountingSystem accountingSystem, Category category) {
        for(Expense expense: category.getExpenses()){
            ExpenseController.removeExpense(accountingSystem, expense);
        }
        for(Income income: category.getIncomes()){
            IncomeController.removeIncome(accountingSystem, income);
        }

        List<Category> categories = accountingSystem.getCategories();
        return categories.remove(category);
    }

    public static Category getCategoryByTitle(
            AccountingSystem accountingSystem, String requestedTitle) {
        List<Category> categories = accountingSystem.getCategories();
        return categories.stream()
                .filter(category -> category.getTitle().equals(requestedTitle))
                .findFirst()
                .orElse(null);
    }

    public static String addCategory(AccountingSystem accountingSystem, Category category) {
        if (findCategoryByName(accountingSystem.getCategories(), category.getTitle()) == null) {
            accountingSystem.getCategories().add(category);
            return "Category added";
        } else {
            return "Category with this name already exists";
        }
    }

    public static String addUser(AccountingSystem accountingSystem, User user) {
            accountingSystem.getUsers().add(user);
            return "User added";
    }

    public static List<Category> findAllParentCategories(AccountingSystem accountingSystem) {
        return accountingSystem.getCategories();
    }

    public static List<User> findAllUsers(AccountingSystem accountingSystem) {
        return accountingSystem.getUsers();
    }

    public static User findUserByName(AccountingSystem accountingSystem, String userName) {
        List<User> users = accountingSystem.getUsers();
        return users.stream().filter(user -> user.getName().equals(userName)).findFirst().orElse(null);
    }

    public static int userNameCount(AccountingSystem accountingSystem, String userName) {
        List<User> users = accountingSystem.getUsers();
        int foundUsers = 0;

        for(User user: users){
            if(user.getName().equals(userName)){
                foundUsers++;
            }
        }
        return foundUsers;
    }

    public static Boolean categoryExists(List<Category> categories, String categoryName) {
        return categories.stream()
                .anyMatch(
                        (category -> category.getTitle().equalsIgnoreCase(categoryName.replaceAll("\\s", ""))));
    }

    public static int getIncome(AccountingSystem accountingSystem) {
        return accountingSystem.getIncome();
    }

    public static int getExpense(AccountingSystem accountingSystem) {
        return accountingSystem.getExpense();
    }

    public static AccountingSystem removeUserForUpdate(AccountingSystem accountingSystem, User activeUser) {
        AccountingSystem accountingSystemUpdated = accountingSystem;
        accountingSystemUpdated.getUsers().remove(activeUser);
        return accountingSystemUpdated;
    }

    public static void removeUser(AccountingSystem accountingSystem, User activeUser) {
        removeUserResponsibilities(activeUser, accountingSystem.getCategories());
        accountingSystem.getUsers().remove(activeUser);
    }

    public static Category findCategoryByName(List<Category> categories, String categoryName) {
        Category foundCategory = null;
        for (Category category : categories) {
            if (category.getTitle().equals(categoryName)) {
                foundCategory = category;
                break;
            } else if (findCategoryByName(category.getSubCategories(), categoryName) != null) {
                foundCategory = findCategoryByName(category.getSubCategories(), categoryName);
                break;
            }
        }
        return foundCategory;
    }

    public static void removeUserResponsibilities(User activeUser, List<Category> categories) {
        if (categories != null) {
            for (Category category : categories) {
                category.getResponsibleUsers().remove(activeUser);
                removeUserResponsibilities(activeUser, category.getSubCategories());
            }
        }
    }
}
