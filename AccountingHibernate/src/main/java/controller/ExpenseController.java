package controller;


import model.AccountingSystem;
import model.Category;
import model.Expense;

import java.util.List;

public class ExpenseController {

    public static Boolean removeExpense(AccountingSystem accountingSystem, Category category, Expense expense) {
        accountingSystem.decreaseExpense(expense.getAmount());
        return CategoryController.removeExpense(category, expense);
    }

    public static Boolean expenseExists(List<Expense> expenses, String expenseName) {
        return expenses.stream().anyMatch((expense -> expense.getName().equalsIgnoreCase(expenseName.replaceAll("\\s", ""))));
    }

    public static Boolean addToCategory(Category category, Expense expense) {
        if (expenseExists(category.getExpenses(), expense.getName())) {
            System.out.println("Expense with name '" + expense.getName() + "' already exists");
            return false;
        } else {
            return category.getExpenses().add(expense);
        }
    }

    public static void createExpense(AccountingSystem accountingSystem, Category category, Expense expense) {
        category.getExpenses().add(expense);
        accountingSystem.addExpense(expense.getAmount());
    }
}
