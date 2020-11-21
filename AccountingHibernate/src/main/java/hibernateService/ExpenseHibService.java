package hibernateService;

import model.AccountingSystem;
import model.Category;
import model.Expense;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import persistenceController.ExpenseHibController;

import javax.persistence.EntityManagerFactory;

public class ExpenseHibService {
    public static void create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Expense expense, Category category) {
        accountingSystem.addExpense(expense.getAmount());
        category.getExpenses().add(expense);
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        expenseHibController.create(expense);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystemHib.update(accountingSystem);
    }

    public static boolean delete(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, Category category, Expense expense) throws Exception {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        categoryHibController.removeExpenseFromCategory(category, expense);
        ExpenseHibController expenseHibController = new ExpenseHibController(entityManagerFactory);
        expenseHibController.delete(expense.getId());

        return true;
    }
}
