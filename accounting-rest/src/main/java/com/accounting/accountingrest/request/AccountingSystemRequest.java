package com.accounting.accountingrest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountingSystemRequest {
    private String name;
    private String systemVersion;
}
