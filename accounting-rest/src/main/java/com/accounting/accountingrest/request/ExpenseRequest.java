package com.accounting.accountingrest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExpenseRequest {
    private String name;
    private Integer amount;
    private int categoryID;
}
