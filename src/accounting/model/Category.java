package accounting.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String title;
    private String description;
    private ArrayList<User> responsibleUsers;
    private Category parentCategory;
    private ArrayList<Category> subCategories = new ArrayList<>();
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Income> incomes = new ArrayList<>();

    public Category(String title, String description, ArrayList<User> responsibleUsers) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
    }

    public Category(String title, String description, ArrayList<User> responsibleUsers, Category parentCategory) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
        this.parentCategory = parentCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(ArrayList<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }
}
