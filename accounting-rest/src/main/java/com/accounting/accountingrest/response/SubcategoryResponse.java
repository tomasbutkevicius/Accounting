package com.accounting.accountingrest.response;

import com.accounting.accountingrest.hibernate.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryResponse {
    private int id;
    private String title;
    private String description;

    public SubcategoryResponse(Category category){
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
    }
}
