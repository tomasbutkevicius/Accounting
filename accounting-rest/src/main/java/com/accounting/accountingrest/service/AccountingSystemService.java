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
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");

    @Autowired
    public AccountingSystemService(){
    }

    public List<AccountingSystemResponse> findAll() {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        List<AccountingSystem> accountingSystems = accountingSystemHib.getAccountingSystemList();
        List<AccountingSystemResponse> responseList = new ArrayList<>();

        for (AccountingSystem accountingSystem : accountingSystems) {
            responseList.add(new AccountingSystemResponse(accountingSystem));
        }
        return responseList;
    }

    public String createAccountingSystem(final AccountingSystemRequest accountingSystemRequest) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        if (accountingSystemHib.getByName(accountingSystemRequest.getName()) != null){
            return "Toks vardas jau yra";
        }
        AccountingSystem accountingSystem = new AccountingSystem(
                accountingSystemRequest.getName(),
                LocalDate.now(),
                accountingSystemRequest.getSystemVersion(),
                0,0);

        return accountingSystemHib.create(accountingSystem);
    }
}
