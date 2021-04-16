package com.accounting.accountingrest.validator;

import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccSysServiceValidator {
    private AccountingSystemHib accountingSystemHib;

    public AccSysServiceValidator(AccountingSystemHib accountingSystemHib){
        this.accountingSystemHib = accountingSystemHib;
    }

    public void validateFind(int id) {
        AccountingSystem accountingSystem = accountingSystemHib.getById(id);
        if (accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounting system not found");
    }

    public void validateCreate(AccountingSystemRequest accountingSystemRequest) {
        if (accountingSystemRequest.getName() == null || accountingSystemRequest.getSystemVersion() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body");
        }
        if (accountingSystemHib.getByName(accountingSystemRequest.getName()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "System name taken");
        }
    }

    public void validateUpdate(AccountingSystem accountingSystem) {
        if(accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid parameters");

        if (accountingSystem.getName() == null || accountingSystem.getSystemVersion() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid parameters");
    }

}
