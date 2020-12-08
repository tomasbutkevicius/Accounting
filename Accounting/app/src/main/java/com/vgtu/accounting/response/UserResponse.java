package com.vgtu.accounting.response;

import com.vgtu.accounting.model.UserType;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private int id;
    private String name;
    private UserType type;
    private String password;
    private String contactInformation;
    private int accountingSystemID;

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public int getAccountingSystemID() {
        return accountingSystemID;
    }

    public void setAccountingSystemID(int accountingSystemID) {
        this.accountingSystemID = accountingSystemID;
    }
}
