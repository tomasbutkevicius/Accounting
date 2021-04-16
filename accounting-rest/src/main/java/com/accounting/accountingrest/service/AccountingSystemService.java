package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.response.AccountingSystemResponse;
import com.accounting.accountingrest.validator.AccSysServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingSystemService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
    private AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    private AccSysServiceValidator accSysServiceValidator = new AccSysServiceValidator(accountingSystemHib);

    @Autowired
    public AccountingSystemService() {
    }

    public List<AccountingSystemResponse> findAll() {
        List<AccountingSystem> accountingSystems = accountingSystemHib.getAccountingSystemList();
        List<AccountingSystemResponse> responseList = new ArrayList<>();

        for (AccountingSystem accountingSystem : accountingSystems) {
            responseList.add(new AccountingSystemResponse(accountingSystem));
        }
        return responseList;
    }

    public HttpStatus createAccountingSystem(final AccountingSystemRequest accountingSystemRequest) {
        accSysServiceValidator.validateCreate(accountingSystemRequest);
        AccountingSystem accountingSystem = new AccountingSystem(
                accountingSystemRequest.getName(),
                LocalDate.now(),
                accountingSystemRequest.getSystemVersion(),
                0, 0);
        accountingSystemHib.create(accountingSystem);
        return HttpStatus.CREATED;
    }


    public String updateAccountingSystem(AccountingSystemRequest accountingSystemRequest, int id) {
        AccountingSystem accountingSystem = accountingSystemHib.getById(id);
        accSysServiceValidator.validateUpdate(accountingSystem);

        accountingSystem.setName(accountingSystemRequest.getName());
        accountingSystem.setSystemVersion(accountingSystemRequest.getSystemVersion());
        accountingSystemHib.update(accountingSystem);
        return "Accounting system is updated";
    }

    public void deleteAccountingSystem(int id) {
        accSysServiceValidator.validateFind(id);
        accountingSystemHib.delete(id);
    }

    public AccountingSystemResponse findAccountingSystem(int id) {
        accSysServiceValidator.validateFind(id);
        return new AccountingSystemResponse(accountingSystemHib.getById(id));
    }
}
