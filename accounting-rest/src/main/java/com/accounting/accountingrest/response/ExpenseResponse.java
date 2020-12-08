package com.accounting.accountingrest.response;

import com.accounting.accountingrest.hibernate.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private int id;
    private String name;
    private Integer amount;
    private LocalDate creationDate;
    private int categoryID;

    public ExpenseResponse(Expense expense){
        this.id = expense.getId();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.categoryID = expense.getCategory().getId();
        this.creationDate = expense.getCreationDate();
    }
}
