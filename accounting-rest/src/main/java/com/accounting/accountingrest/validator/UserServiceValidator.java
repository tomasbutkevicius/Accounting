package com.accounting.accountingrest.validator;

import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.request.LoginRequest;
import com.accounting.accountingrest.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class UserServiceValidator {
    private UserHibController userHibController;
    private AccountingSystemHib accountingSystemHib;

    public UserServiceValidator(UserHibController userHibController, AccountingSystemHib accountingSystemHib){
        this.userHibController = userHibController;
        this.accountingSystemHib = accountingSystemHib;
    }

    public void validateFind(int id) {
        User user = userHibController.getById(id);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    public void validateCreate(UserRequest userRequest) {
        AccountingSystem accountingSystem = accountingSystemHib.getById(userRequest.getAccountingSystemID());
        if(accountingSystem == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");
        }

        for(User user: userHibController.getAllUsersInSystem(accountingSystem)){
            if(user.getName().equalsIgnoreCase(userRequest.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with name already exists");
            }
        }

        if(userRequest.getType().equalsIgnoreCase("ADMIN"))
        {
            for(User user: userHibController.getUserList()){
                if(user.getName().equalsIgnoreCase(userRequest.getName())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with name already exists");
                }
            }
        }

        if(!userRequest.getType().equalsIgnoreCase("private") && !userRequest.getType().equalsIgnoreCase("company")
                && !userRequest.getType().equalsIgnoreCase("admin"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given type does not exist");
    }

    public void validateUpdate(int id, UserRequest userRequest) {
        if(userRequest.getType() == null || userRequest.getName() == null || userRequest.getPassword() == null || userRequest.getContactInformation() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        User user = userHibController.getById(id);
        if(user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

        AccountingSystem accountingSystem = accountingSystemHib.getById(userRequest.getAccountingSystemID());
        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");

        if (!userRequest.getName().equals(user.getName()) && (userNameCount(accountingSystem, userRequest.getName()) >= 1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this name already exists");
        }

        if(!userRequest.getType().equalsIgnoreCase("private") && !userRequest.getType().equalsIgnoreCase("company")
                && !userRequest.getType().equalsIgnoreCase("admin"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given type does not exist");
    }

    public void validateLoginRequest(LoginRequest loginRequest, AccountingSystem accountingSystem) {
        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "System not found");

        if(loginRequest.getName() == null || loginRequest.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");
    }

    private int userNameCount(AccountingSystem accountingSystem, String userName) {
        List<User> users = accountingSystem.getUsers();
        int foundUsers = 0;

        for(User user: users){
            if(user.getName().equals(userName)){
                foundUsers++;
            }
        }
        return foundUsers;
    }
}
