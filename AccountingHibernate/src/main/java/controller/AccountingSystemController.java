package controller;


import model.AccountingSystem;
import model.Category;
import model.User;
import persistenceController.AccountingSystemHib;

import java.util.List;

public class AccountingSystemController {

    public static Boolean removeCategory(AccountingSystem accountingSystem, Category category) {
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
        if (UserController.getUserByName(accountingSystem, user.getName()) == null) {
            accountingSystem.getUsers().add(user);
            return "User added";
        } else {
            return "User already exists";
        }
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

    public static void removeUserForUpdate(AccountingSystem accountingSystem, User activeUser) {
        accountingSystem.getUsers().remove(activeUser);
    }

    public static void removeUser(AccountingSystem accountingSystem, User activeUser) {
        removeUserResponsiblities(activeUser, accountingSystem.getCategories());
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

    public static void removeUserResponsiblities(User activeUser, List<Category> categories) {
        if (categories != null) {
            for (Category category : categories) {
                category.getResponsibleUsers().remove(activeUser);
                removeUserResponsiblities(activeUser, category.getSubCategories());
            }
        }
    }
}
