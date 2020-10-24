package accounting.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccountingSystem implements Serializable {
    private String name;
    private LocalDate systemCreationDate;
    private String systemVersion;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private int income;
    private int expense;

    public AccountingSystem() {
    }

    public AccountingSystem(String name, LocalDate systemCreationDate, String systemVersion, int income, int expense) {
        this.name = name;
        this.systemCreationDate = systemCreationDate;
        this.systemVersion = systemVersion;
        this.income = income;
        this.expense = expense;
    }

    public void addIncome(int income) {
        this.income += income;
    }

    @Override
    public String toString() {
        return "name is " + name + "\n users: \n" +
                "\t " + users.toString();
    }

    public void updateIncome(Integer amount, Integer newAmount) {
        this.income -= amount;
        this.income += newAmount;
    }

    public void addExpense(int expense) {
        this.expense += expense;
    }

    public void updateExpense(int amount, int newAmount) {
        this.expense -= amount;
        this.expense += newAmount;
    }

    public void decreaseIncome(int amount) {
        this.income -= amount;
    }

    public void decreaseExpense(int amount) {
        this.expense -= amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getSystemCreationDate() {
        return systemCreationDate;
    }

    public void setSystemCreationDate(LocalDate systemCreationDate) {
        this.systemCreationDate = systemCreationDate;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}
