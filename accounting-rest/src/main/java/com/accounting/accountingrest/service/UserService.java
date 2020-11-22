package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.controller.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.controller.UserHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.model.UserType;
import com.accounting.accountingrest.hibernate.service.UserServiceHib;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.request.UserRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounting system not found");
        }

        UserHibController userHibController = new UserHibController(entityManagerFactory);
        for(User user: userHibController.getAllUsersInSystem(accountingSystem)){
            if(user.getName().equalsIgnoreCase(userRequest.getName())){
                return "user with the name already exists";
            }
        }

        if(!userRequest.getType().equalsIgnoreCase("private") && !userRequest.getType().equalsIgnoreCase("company")
        && !userRequest.getType().equalsIgnoreCase("admin"))
            return "given type does not exist";

        UserType userType = UserType.valueOf(userRequest.getType().toUpperCase());

        User user = new User(
                userType,
                userRequest.getName(),
                userRequest.getPassword(),
                userRequest.getContactInformation());

        return UserServiceHib.create(entityManagerFactory, accountingSystem, user);
    }
}
