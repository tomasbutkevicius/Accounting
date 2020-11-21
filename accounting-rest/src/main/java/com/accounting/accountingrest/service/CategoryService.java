package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.controller.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.controller.CategoryHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CategoryHibController categoryHibController ;

    @Autowired
    public CategoryService(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
        this.categoryHibController = new CategoryHibController(entityManagerFactory);
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryHibController.getCategoryList();
        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(new CategoryResponse(category));
        }
        return responseList;
    }

//    public String create(final AccountingSystemRequest accountingSystemRequest) {
//        if (accountingSystemHib.getByName(accountingSystemRequest.getName()) != null){
//            System.out.println("Toks vardas jau yra");
//        }
//        AccountingSystem accountingSystem = new AccountingSystem(
//                accountingSystemRequest.getName(),
//                LocalDate.now(),
//                accountingSystemRequest.getSystemVersion(),
//                0,0);
//
//        return accountingSystemHib.create(accountingSystem);
//    }
}
