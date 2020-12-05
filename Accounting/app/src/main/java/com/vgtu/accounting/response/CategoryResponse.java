package com.vgtu.accounting.response;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private int id;
    private String description;
    private String title;
    private int accountingSystemID;
    private int parentCategoryID;
    private List<IncomeResponse> incomes = new ArrayList<>();
    private List<ExpenseResponse> expenses = new ArrayList<>();
    private List<UserResponse> responsibleUsers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAccountingSystemID() {
        return accountingSystemID;
    }

    public void setAccountingSystemID(int accountingSystemID) {
        this.accountingSystemID = accountingSystemID;
    }

    public int getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(int parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    public List<IncomeResponse> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<IncomeResponse> incomes) {
        this.incomes = incomes;
    }

    public List<ExpenseResponse> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseResponse> expenses) {
        this.expenses = expenses;
    }

    public List<UserResponse> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<UserResponse> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }
}
