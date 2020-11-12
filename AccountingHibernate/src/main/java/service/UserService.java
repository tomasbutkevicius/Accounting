package service;

import model.AccountingSystem;
import model.User;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;

public class UserService {
    public static String create(AccountingSystemHib accountingSystemHib, AccountingSystem accountingSystem, User user){
        user.setAccountingSystem(accountingSystem);
        accountingSystem.getUsers().add(user);
        accountingSystemHib.update(accountingSystem);
        return "New user added";
    }
}
