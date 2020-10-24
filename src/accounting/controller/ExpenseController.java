package accounting.controller;

import accounting.model.AccountingSystem;
import accounting.model.Category;
import accounting.model.Expense;

import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseController {

    public static Boolean updateExpense(AccountingSystem accountingSystem, Category selectedCategory) {
        System.out.println("Enter name of expense to update");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Expense expense = CategoryController.getExpenseByName(selectedCategory, name);

        if (expense == null) {
            System.out.println("Expense to update was not found");
            return false;
        } else {
            System.out.println("Enter new expense name");
            String newName = scanner.nextLine();
            expense.setName(newName);
            System.out.println("Enter number of expense amount (eur) ");
            Integer newAmount = scanner.nextInt();
            accountingSystem.updateExpense(expense.getAmount(), newAmount);
            expense.setAmount(newAmount);
            System.out.println("Expense updated");
            return true;
        }
    }

    public static Boolean removeExpense(AccountingSystem accountingSystem, Category category, Expense expense) {
        accountingSystem.decreaseExpense(expense.getAmount());
        return CategoryController.removeExpense(category, expense);
    }

    public static Boolean expenseExists(ArrayList<Expense> expenses, String expenseName) {
        return expenses.stream().anyMatch((expense -> expense.getName().equals(expenseName)));
    }

    public static Boolean addToCategory(Category category, Expense expense) {
        if (expenseExists(category.getExpenses(), expense.getName())) {
            System.out.println("Expense with name '" + expense.getName() + "' already exists");
            return false;
        } else {
            return category.getExpenses().add(expense);
        }
    }

    public static Expense createExpense(AccountingSystem accountingSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Expense name");
        String name = scanner.nextLine();
        System.out.println("Enter number of expense amount (eur)");
        Integer amount = scanner.nextInt();
        accountingSystem.addExpense(amount);
        return new Expense(name, amount);
    }
}
