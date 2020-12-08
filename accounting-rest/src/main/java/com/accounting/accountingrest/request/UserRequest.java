package com.accounting.accountingrest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String password;
    private String contactInformation;
    private String type;
    private int accountingSystemID;
}
