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
import com.accounting.accountingrest.validator.UserServiceValidator;
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
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    UserServiceValidator userServiceValidator = new UserServiceValidator(userHibController, accountingSystemHib);

    @Autowired
    public UserService(){
    }

    public List<UserResponse> findAll() {
        List<User> userList = userHibController.getUserList();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            responseList.add(new UserResponse(user));
        }
        return responseList;
    }

    public String createUser(final UserRequest userRequest) {
       userServiceValidator.validateCreate(userRequest);

        UserType userType = UserType.valueOf(userRequest.getType().toUpperCase());

        User user = new User(
                userType,
                userRequest.getName(),
                userRequest.getPassword(),
                userRequest.getContactInformation());

        return UserServiceHib.create(entityManagerFactory, accountingSystemHib.getById(userRequest.getAccountingSystemID()), user);
    }

    public String updateUser(UserRequest userUpdated, int id) {
        userServiceValidator.validateUpdate(id, userUpdated);

        User user = userHibController.getById(id);
        AccountingSystem accountingSystem = accountingSystemHib.getById(userUpdated.getAccountingSystemID());

        user.setAccountingSystem(accountingSystem);
        user.setName(userUpdated.getName());
        user.setPassword(userUpdated.getPassword());
        user.setContactInformation(userUpdated.getContactInformation());
        user.setType(UserType.valueOf(userUpdated.getType().toUpperCase()));

        return userHibController.update(user);
    }

    public void deleteUser(int id) {
        userServiceValidator.validateFind(id);
        userHibController.delete(id);
    }

    public UserResponse findUser(int id) {
        userServiceValidator.validateFind(id);
        return new UserResponse(userHibController.getById(id));
    }

    public UserResponse login(LoginRequest loginRequest, int systemID) {
        AccountingSystem accountingSystem = accountingSystemHib.getById(systemID);

        userServiceValidator.validateLoginRequest(loginRequest, accountingSystem);

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
