package com.vgtu.accounting.response;
import java.io.Serializable;
import java.time.LocalDate;

public class AccountingSystemResponse implements Serializable {
    private int id;
    private String name;
    private String systemCreationDate;
    private String systemVersion;
    private int income;
    private int expense;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemCreationDate() {
        return systemCreationDate;
    }

    public void setSystemCreationDate(String systemCreationDate) {
        this.systemCreationDate = systemCreationDate;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
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
