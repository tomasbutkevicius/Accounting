package com.accounting.accountingrest.response;


import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String name;
    private UserType type;
    private String password;
    private String contactInformation;
    private int accountingSystemID;

    public UserResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.accountingSystemID = user.getAccountingSystem().getId();
        this.contactInformation = user.getContactInformation();
        this.password = user.getPassword();
        this.type = user.getType();
    }
}
