package service;

import model.AccountingSystem;
import model.User;
import persistenceController.AccountingSystemHib;

public class AccountingSystemService {
    public static void create(AccountingSystemHib accountingSystemHib, AccountingSystem accountingSystem, User user){
        user.setAccountingSystem(accountingSystem);
        accountingSystem.getUsers().add(user);
        accountingSystemHib.create(accountingSystem);
    }
}
