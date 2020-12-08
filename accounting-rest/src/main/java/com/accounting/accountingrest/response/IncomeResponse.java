package com.accounting.accountingrest.response;

import com.accounting.accountingrest.hibernate.model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponse {
    private int id;
    private String name;
    private Integer amount;
    private int categoryID;

    public IncomeResponse(Income income){
        this.id = income.getId();
        this.name = income.getName();
        this.amount = income.getAmount();
        this.categoryID = income.getCategory().getId();
    }
}
