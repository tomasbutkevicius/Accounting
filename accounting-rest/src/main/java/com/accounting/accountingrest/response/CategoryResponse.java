package com.accounting.accountingrest.response;

import com.accounting.accountingrest.hibernate.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private int id;
    private String description;
    private String title;
    private int accountingSystemID;
    private int parentCategoryID;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.description = category.getDescription();
        this.title = category.getTitle();
        this.accountingSystemID = category.getAccountingSystem().getId();
        if(category.getParentCategory() == null){
            this.parentCategoryID = 0;
        } else {
            this.parentCategoryID = category.getParentCategory().getId();
        }
    }
}
