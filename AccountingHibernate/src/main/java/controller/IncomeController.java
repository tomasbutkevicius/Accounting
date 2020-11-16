package controller;


import model.AccountingSystem;
import model.Category;
import model.Income;

import java.util.List;

public class IncomeController {

    public static Boolean removeIncome(AccountingSystem accountingSystem, Income income) {
        return accountingSystem.decreaseIncome(income.getAmount());
    }

    public static Boolean incomeExists(List<Income> incomes, String incomeName) {
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
