package hibernateService;

import model.AccountingSystem;
import model.User;
import persistenceController.AccountingSystemHib;

public class AccountingSystemHibService {
    public static void create(AccountingSystemHib accountingSystemHib, AccountingSystem accountingSystem, User user){
        user.setAccountingSystem(accountingSystem);
        accountingSystem.getUsers().add(user);
        accountingSystemHib.create(accountingSystem);
    }
}
