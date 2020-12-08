package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
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
public class AccountingSystemService {
    //To run: XAMPP Apache, MySQL was used
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


    public String updateAccountingSystem(AccountingSystemRequest accountingSystemRequest, int id) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(id);
        if(accountingSystemRequest.getName() == null || accountingSystemRequest.getSystemVersion() == null || accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid parameters");

        accountingSystem.setName(accountingSystemRequest.getName());
        accountingSystem.setSystemVersion(accountingSystemRequest.getSystemVersion());

        return accountingSystemHib.update(accountingSystem);
    }

    public void deleteAccountingSystem(int id) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(id);
        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounting system not found");
        accountingSystemHib.delete(id);
    }

    public AccountingSystemResponse findAccountingSystem(int id) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(id);
        if(accountingSystem == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "System not found");
        }

        return new AccountingSystemResponse(accountingSystem);
    }
}
