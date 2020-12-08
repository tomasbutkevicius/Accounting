package controller;

import model.*;
import persistenceController.CategoryHibController;
import service.CategoryService;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class CategoryController {

    public static Category getCategory(AccountingSystem accountingSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter category name");
        String requestedName = scanner.nextLine();
        if (AccountingSystemController.categoryExists(
                accountingSystem.getCategories(), requestedName)) {
            Category category =
                    AccountingSystemController.getCategoryByTitle(accountingSystem, requestedName);
            System.out.println(category.getTitle() + " FOUND");
            return category;
        } else {
            return null;
        }
    }

    public static String createSubCategory(AccountingSystem accountingSystem,
                                           Category selectedCategory, Category subcategory, User user) {
        if (AccountingSystemController.findCategoryByName(accountingSystem.getCategories(), subcategory.getTitle()) != null) {
            return "Category with this name already exists";
        } else {
            Category subCategory;
            subCategory =
                    new Category(
                            subcategory.getTitle(),
                            subcategory.getDescription(),
                            subcategory.getResponsibleUsers(),
                            selectedCategory);
            addSubCategory(selectedCategory, subCategory);
            return "Subcategory added";
        }
    }

    private static void addSubCategory(Category selectedCategory, Category subCategory) {
        selectedCategory.getSubCategories().add(subCategory);
    }

    public static void updateCategoryMainInfo(
            Category selectedCategory, String newTitle, String newDescription) {
        selectedCategory.setTitle(newTitle);
        selectedCategory.setDescription(newDescription);
    }

    public static List<Category> getAllParentCategories(AccountingSystem accountingSystem) {
        return AccountingSystemController.findAllParentCategories(accountingSystem);
    }

    public static String addResponsibleUser(Category selectedCategory, User responsibleUser, EntityManagerFactory entityManagerFactory) throws Exception {
        if (responsibleUser.getType().equals(UserType.ADMIN)) return "Admin cannot be responsible";
        CategoryService.addResponsibleUser(selectedCategory, responsibleUser, entityManagerFactory);
        return "Added new responsible user";
    }

    public static Boolean responsibleUserExists(List<User> responsibleUsers, User user) {
        return responsibleUsers.stream()
                .anyMatch(responsibleUser -> responsibleUser.getName().equals(user.getName()));
    }

    public static void removeResponsibleUser(
            AccountingSystem accountingSystem, Category selectedCategory) {
        System.out.println("Enter responsible person username to remove from category");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        User user = UserController.getUserByName(accountingSystem, userName);
        if (user == null) {
            System.out.println("User with this name does not exist");
        } else {
            if (CategoryController.responsibleUserExists(selectedCategory.getResponsibleUsers(), user)) {
                List<User> responsibleUsers = selectedCategory.getResponsibleUsers();
                responsibleUsers.remove(user);
                System.out.println(
                        "Removed user '"
                                + user.getName()
                                + "' from responsible list for '"
                                + selectedCategory.getTitle()
                                + "'");
            } else {
                System.out.println("This user is not assigned as responsible");
            }
        }
    }

    public static Category getSubcategoryByName(Category selectedCategory, String subCategoryName) {
        List<Category> subCategories = selectedCategory.getSubCategories();
        return subCategories.stream()
                .filter(category -> category.getTitle().equals(subCategoryName))
                .findFirst()
                .orElse(null);
    }

    public static Boolean removeSubCategory(Category selectedCategory, Category subCategory) {
        List<Category> subCategories = selectedCategory.getSubCategories();
        return subCategories.remove(subCategory);
    }

    public static Income getIncomeByName(Category selectedCategory, String incomeName) {
        List<Income> incomes = selectedCategory.getIncomes();
        return incomes.stream()
                .filter(income -> income.getName().equals(incomeName))
                .findFirst()
                .orElse(null);
    }

    public static Boolean removeIncome(Category selectedCategory, Income incomeToRemove) {
        List<Income> incomes = selectedCategory.getIncomes();
        return incomes.remove(incomeToRemove);
    }

    public static Expense getExpenseByName(Category selectedCategory, String expenseName) {
        List<Expense> expenses = selectedCategory.getExpenses();
        return expenses.stream()
                .filter(expense -> expense.getName().equals(expenseName))
                .findFirst()
                .orElse(null);
    }

    public static boolean removeExpense(Category selectedCategory, Expense expenseToRemove) {
        List<Expense> expenses = selectedCategory.getExpenses();
        return expenses.remove(expenseToRemove);
    }
}
