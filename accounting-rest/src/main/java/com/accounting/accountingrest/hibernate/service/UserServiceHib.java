package com.accounting.accountingrest.hibernate.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserServiceHib {
    public static String create(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, User user){
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        user.setAccountingSystem(accountingSystem);
        accountingSystem.getUsers().add(user);
        accountingSystemHib.update(accountingSystem);
        return "New user added";
    }

    public static User login(
            EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem, String userName, String password) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accountingSystem =  accountingSystemHib.getById(accountingSystem.getId());
        List<User> users = accountingSystem.getUsers();
        return users.stream()
                .filter(user -> user.getName().equals(userName) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public static List<User> getAllUsersInSystem(EntityManagerFactory entityManagerFactory, AccountingSystem accountingSystem) {
        UserHibController userHibController = new UserHibController(entityManagerFactory);
        return userHibController.getAllUsersInSystem(accountingSystem);
    }
}
