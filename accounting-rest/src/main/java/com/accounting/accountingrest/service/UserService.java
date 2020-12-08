package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.model.UserType;
import com.accounting.accountingrest.hibernate.service.UserServiceHib;
import com.accounting.accountingrest.request.LoginRequest;
import com.accounting.accountingrest.request.UserRequest;
import com.accounting.accountingrest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");

    @Autowired
    public UserService(){
    }

    public List<UserResponse> findAll() {
        UserHibController userHibController = new UserHibController(entityManagerFactory);
        List<User> userList = userHibController.getUserList();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            responseList.add(new UserResponse(user));
        }
        return responseList;
    }

    public String createUser(final UserRequest userRequest) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(userRequest.getAccountingSystemID());
        if(accountingSystem == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");
        }

        UserHibController userHibController = new UserHibController(entityManagerFactory);
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

        UserType userType = UserType.valueOf(userRequest.getType().toUpperCase());

        User user = new User(
                userType,
                userRequest.getName(),
                userRequest.getPassword(),
                userRequest.getContactInformation());

        return UserServiceHib.create(entityManagerFactory, accountingSystem, user);
    }

    public String updateUser(UserRequest userUpdated, int id) {
        if(userUpdated.getType() == null || userUpdated.getName() == null || userUpdated.getPassword() == null || userUpdated.getContactInformation() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

    UserHibController userHibController = new UserHibController(entityManagerFactory);
    User user = userHibController.getById(id);
    if(user == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

    AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);

    AccountingSystem accountingSystem = accountingSystemHib.getById(userUpdated.getAccountingSystemID());
        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");

        if (!userUpdated.getName().equals(user.getName()) && (userNameCount(accountingSystem, userUpdated.getName()) >= 1)) {
            return "User with this name already exists";
        }

        user.setAccountingSystem(accountingSystem);
        user.setName(userUpdated.getName());
        user.setPassword(userUpdated.getPassword());
        user.setContactInformation(userUpdated.getContactInformation());
        user.setType(UserType.valueOf(userUpdated.getType().toUpperCase()));

        return userHibController.update(user);
    }

    public static int userNameCount(AccountingSystem accountingSystem, String userName) {
        List<User> users = accountingSystem.getUsers();
        int foundUsers = 0;

        for(User user: users){
            if(user.getName().equals(userName)){
                foundUsers++;
            }
        }
        return foundUsers;
    }

    public void deleteUser(int id) {
        UserHibController userHibController = new UserHibController(entityManagerFactory);
        User user = userHibController.getById(id);
        if(user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        userHibController.delete(id);
    }

    public UserResponse findUser(int id) {
        UserHibController userHibController = new UserHibController(entityManagerFactory);
        User user = userHibController.getById(id);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        return new UserResponse(user);
    }

    public UserResponse login(LoginRequest loginRequest, int systemID) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(systemID);
        System.out.println("SYSTEM FOUNDDD");

        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "System not found");

        if(loginRequest.getName() == null || loginRequest.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        UserHibController userHibController = new UserHibController(entityManagerFactory);
        List<User> users = userHibController.getAllUsersInSystem(accountingSystem);
        UserResponse userResponse = null;

        for(User user: users){
            if(user.getName().equals(loginRequest.getName()) && user.getPassword().equals(loginRequest.getPassword()) ){
                userResponse = new UserResponse(user);
            }
        }

        if(userResponse == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return userResponse;
    }
}
