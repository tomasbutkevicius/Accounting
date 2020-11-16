package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AccountingSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate systemCreationDate;
    private String systemVersion;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "accountingSystem", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<User> users = new ArrayList<>();
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "accountingSystem", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<Category> categories = new ArrayList<>();
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

    public AccountingSystem(int id, String name, LocalDate systemCreationDate, String systemVersion, List<User> users, List<Category> categories, int income, int expense) {
        this.id = id;
        this.name = name;
        this.systemCreationDate = systemCreationDate;
        this.systemVersion = systemVersion;
        this.users = users;
        this.categories = categories;
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

    public boolean decreaseIncome(int amount) {
        this.income -= amount;
        return true;
    }

    public boolean decreaseExpense(int amount) {
        this.expense -= amount;
        return true;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<Category> getCategories() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
