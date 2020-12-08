package com.accounting.accountingrest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountingSystemRequest {
    private String name;
    private String systemVersion;
}
