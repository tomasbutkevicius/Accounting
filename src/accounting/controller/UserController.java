package accounting.controller;

import accounting.model.AccountingSystem;
import accounting.model.User;
import accounting.model.UserType;

import java.util.ArrayList;
import java.util.Scanner;

public class UserController {

    public static void updateUser(User activeUser) {
        String userTypeInput;
        String userName;
        String userPassword;
        String contactInfo;
        System.out.println("Enter 'individual' or 'company' to choose user type");
        Scanner scanner = new Scanner(System.in);
        userTypeInput = scanner.nextLine();
        if (userTypeInput.toUpperCase().equals(UserType.COMPANY.toString())) {
            activeUser.setType(UserType.COMPANY);
            System.out.println("User type is company");
        } else {
            activeUser.setType(UserType.PRIVATE);
            System.out.println("User type is individual");
        }
        System.out.println("Enter user name");
        userName = scanner.nextLine();
        activeUser.setName(userName);
        System.out.println("Enter user password");
        userPassword = scanner.nextLine();
        activeUser.setPassword(userPassword);
        System.out.println("Enter user contact information");
        contactInfo = scanner.nextLine();
        activeUser.setContactInformation(contactInfo);
    }

    public static Boolean userExists(ArrayList<User> users, User currentUser) {
        return users.stream().anyMatch((user -> user.getName().equals(currentUser.getName()) && user.getPassword().equals(currentUser.getPassword())));
    }

    public static void createUser(AccountingSystem accountingSystem) {
        User user = signUpUser(accountingSystem);
        AccountingSystemController.addUser(accountingSystem, user);
    }

    public static User getUserByLogin(AccountingSystem accountingSystem, String userName, String password) {
        return AccountingSystemController.findUserByLogin(accountingSystem, userName, password);
    }

    public static User getUserByName(AccountingSystem accountingSystem, String userName) {
        return AccountingSystemController.findUserByName(accountingSystem, userName);
    }

    public static ArrayList<User> getAllUsers(AccountingSystem accountingSystem) {
        return AccountingSystemController.findAllUsers(accountingSystem);
    }


    private static User signUpUser(AccountingSystem accountingSystem) {
        Scanner scanner = new Scanner(System.in);
        String userTypeInput;
        UserType userType;
        String userName;
        String userPassword;
        String contactInfo;
        System.out.println("Enter 'individual' or 'company' to choose user type");
        userTypeInput = scanner.nextLine();
        if (userTypeInput.toUpperCase().equals(UserType.COMPANY.toString())) {
            userType = UserType.COMPANY;
            System.out.println("User type is company");
        } else {
            userType = UserType.PRIVATE;
            System.out.println("User type is individual");
        }
        System.out.println("Enter user name");
        userName = scanner.nextLine();
        System.out.println("Enter user password");
        userPassword = scanner.nextLine();
        System.out.println("Enter user contact information");
        contactInfo = scanner.nextLine();
        return new User(userType, userName, userPassword, contactInfo);
    }
}
