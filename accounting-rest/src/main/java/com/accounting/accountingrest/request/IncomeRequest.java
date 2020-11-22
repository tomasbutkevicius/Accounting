package com.accounting.accountingrest.request;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncomeRequest {
    private String name;
    private Integer amount;
    private int categoryID;
}
