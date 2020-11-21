package service;

import model.AccountingSystem;
import model.User;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserService {
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
