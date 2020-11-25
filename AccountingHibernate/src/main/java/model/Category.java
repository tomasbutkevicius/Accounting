package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToMany(cascade = {CascadeType.MERGE}, mappedBy = "categories")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<User> responsibleUsers = new ArrayList<>();
    @ManyToOne
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<Category> subCategories = new ArrayList<>();
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<Expense> expenses = new ArrayList<>();
    @ManyToOne
    private  AccountingSystem accountingSystem;


    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Category(String title, String description, List<User> responsibleUsers) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
    }

    public Category(String title, String description, List<User> responsibleUsers, Category parentCategory) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
        this.parentCategory = parentCategory;
    }


    public Category(int id, String title, String description, List<User> responsibleUsers, Category parentCategory, List<Category> subCategories, AccountingSystem accountingSystem) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.accountingSystem = accountingSystem;
    }


    public Category(int id, String title, String description, List<User> responsibleUsers, Category parentCategory, List<Category> subCategories, List<Income> incomes, List<Expense> expenses) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.incomes = incomes;
        this.expenses = expenses;
    }

    public Category() {

    }

    public void removeUser(User user) {
        this.responsibleUsers.remove(user);
        user.getCategories().remove(this);
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

    public List<User> getResponsibleUsers() {
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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }
}
