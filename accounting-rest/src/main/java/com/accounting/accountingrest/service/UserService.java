package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.controller.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.controller.UserHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserHibController userHibController ;

    @Autowired
    public UserService(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
        this.userHibController = new UserHibController(entityManagerFactory);
    }

    public List<UserResponse> findAll() {
        List<User> userList = userHibController.getUserList();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            responseList.add(new UserResponse(user));
        }
        return responseList;
    }
}
