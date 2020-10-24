package accounting.controller;

import accounting.model.AccountingSystem;
import accounting.model.Category;
import accounting.model.User;

import java.util.ArrayList;

public class AccountingSystemController {

    public static Boolean removeCategory(AccountingSystem accountingSystem, Category category) {
        ArrayList<Category> categories = accountingSystem.getCategories();
        return categories.remove(category);
    }

    public static Category getCategoryByTitle(AccountingSystem accountingSystem, String requestedTitle) {
        ArrayList<Category> categories = accountingSystem.getCategories();
        return categories.stream().filter(category -> category.getTitle().equals(requestedTitle)).findFirst().orElse(null);
    }

    public static Boolean addCategory(AccountingSystem accountingSystem, Category category) {
        if (!categoryExists(accountingSystem.getCategories(), category.getTitle())) {
            accountingSystem.getCategories().add(category);
            System.out.println("Category added");
            return true;
        } else {
            System.out.println("Category already exists");
            return false;
        }
    }

    public static void addUser(AccountingSystem accountingSystem, User user) {
        if (!UserController.userExists(accountingSystem.getUsers(), user)) {
            accountingSystem.getUsers().add(user);
            System.out.println("User added");
        } else {
            System.out.println("User already exists");
        }
    }

    public static User findUserByLogin(AccountingSystem accountingSystem, String userName, String password) {
        ArrayList<User> users = accountingSystem.getUsers();
        return users.stream().filter(user -> user.getName().equals(userName) && user.getPassword().equals(password)).findFirst().orElse(null);
    }

    public static ArrayList<Category> findAllParentCategories(AccountingSystem accountingSystem) {
        return accountingSystem.getCategories();
    }

    public static ArrayList<User> findAllUsers(AccountingSystem accountingSystem) {
        return accountingSystem.getUsers();
    }


    public static User findUserByName(AccountingSystem accountingSystem, String userName) {
        ArrayList<User> users = accountingSystem.getUsers();
        return users.stream().filter(user -> user.getName().equals(userName)).findFirst().orElse(null);
    }

    public static Boolean categoryExists(ArrayList<Category> categories, String categoryName) {
        return categories.stream().anyMatch((category -> category.getTitle().equals(categoryName)));
    }

    public static int getIncome(AccountingSystem accountingSystem) {
        return accountingSystem.getIncome();
    }

    public static int getExpense(AccountingSystem accountingSystem) {
        return accountingSystem.getExpense();
    }

    public static Boolean removeUser(AccountingSystem accountingSystem, User activeUser) {
        return accountingSystem.getUsers().remove(activeUser);
    }
}
