package com.accounting.accountingrest.hibernate.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;

public class AccountingSystemServiceHib {
    public static void create(AccountingSystemHib accountingSystemHib, AccountingSystem accountingSystem, User user){
        user.setAccountingSystem(accountingSystem);
        accountingSystem.getUsers().add(user);
        accountingSystemHib.create(accountingSystem);
    }
}
