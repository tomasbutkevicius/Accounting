package com.accounting.accountingrest.response;


import com.accounting.accountingrest.hibernate.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountingSystemResponse {
    private int id;
    private String name;
    private LocalDate systemCreationDate;
    private String systemVersion;
    private int income;
    private int expense;


    public AccountingSystemResponse(AccountingSystem accountingSystem){
        this.id = accountingSystem.getId();
        this.name = accountingSystem.getName();
        this.systemCreationDate = accountingSystem.getSystemCreationDate();
        this.systemVersion = accountingSystem.getSystemVersion();
        this.income = accountingSystem.getIncome();
        this.expense = accountingSystem.getExpense();
    }
}
