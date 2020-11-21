package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.controller.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingSystemService {
    private AccountingSystemHib accountingSystemHib ;

    @Autowired
    public AccountingSystemService(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
        this.accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    }

    public List<AccountingSystemResponse> findAll() {
        List<AccountingSystem> accountingSystems = accountingSystemHib.getAccountingSystemList();
        List<AccountingSystemResponse> responseList = new ArrayList<>();

        for (AccountingSystem accountingSystem : accountingSystems) {
            responseList.add(new AccountingSystemResponse(accountingSystem));
        }
        return responseList;
    }

    public String createAccountingSystem(final AccountingSystemRequest accountingSystemRequest) {
        if (accountingSystemHib.getByName(accountingSystemRequest.getName()) != null){
            System.out.println("Toks vardas jau yra");
        }
        AccountingSystem accountingSystem = new AccountingSystem(
                accountingSystemRequest.getName(),
                LocalDate.now(),
                accountingSystemRequest.getSystemVersion(),
                0,0);

        return accountingSystemHib.create(accountingSystem);
    }
}
