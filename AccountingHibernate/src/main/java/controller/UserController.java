package controller;

import model.AccountingSystem;
import model.User;
import model.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {

    public static Boolean userExists(ArrayList<User> users, User currentUser) {
        return users.stream().anyMatch((user -> user.getName().equals(currentUser.getName()) && user.getPassword().equals(currentUser.getPassword())));
    }

    public static User getUserByName(AccountingSystem accountingSystem, String userName) {
        return AccountingSystemController.findUserByName(accountingSystem, userName);
    }

    public static List<User> getAllUsers(AccountingSystem accountingSystem) {
        return AccountingSystemController.findAllUsers(accountingSystem);
    }

}
