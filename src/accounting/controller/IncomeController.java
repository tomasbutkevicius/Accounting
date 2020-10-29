package accounting.controller;

import accounting.model.AccountingSystem;
import accounting.model.Category;
import accounting.model.Income;

import java.util.ArrayList;
import java.util.Scanner;

public class IncomeController {

    public static Boolean updateIncome(AccountingSystem accountingSystem, Category selectedCategory) {
        System.out.println("Enter name of income to update");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Income income = CategoryController.getIncomeByName(selectedCategory, name);

        if (income == null) {
            System.out.println("Income to update was not found");
            return false;
        } else {
            System.out.println("Enter new income name");
            String newName = scanner.nextLine();
            income.setName(newName);
            System.out.println("Enter number of income amount (eur) ");
            Integer newAmount = scanner.nextInt();
            accountingSystem.updateIncome(income.getAmount(), newAmount);
            income.setAmount(newAmount);
            System.out.println("Income updated");
            return true;
        }
    }

    public static Boolean removeIncome(AccountingSystem accountingSystem, Category category, Income income) {
        accountingSystem.decreaseIncome(income.getAmount());
        return CategoryController.removeIncome(category, income);
    }

    public static Boolean incomeExists(ArrayList<Income> incomes, String incomeName) {
        return incomes.stream().anyMatch((income -> income.getName().equalsIgnoreCase(incomeName.replaceAll("\\s", ""))));
    }

    public static Boolean addToCategory(Category category, Income income) {
        if (incomeExists(category.getIncomes(), income.getName())) {
            System.out.println("Income with name '" + income.getName() + "' already exists");
            return false;
        } else {
            return category.getIncomes().add(income);
        }
    }

    public static void createIncome(AccountingSystem accountingSystem, Category category, Income income) {
        category.getIncomes().add(income);
        accountingSystem.addIncome(income.getAmount());
    }
}
