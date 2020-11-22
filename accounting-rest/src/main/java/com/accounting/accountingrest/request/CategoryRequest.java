package com.accounting.accountingrest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String title;
    private String description;
    private int accountingSystemID;
    private int parentCategoryID;
}
