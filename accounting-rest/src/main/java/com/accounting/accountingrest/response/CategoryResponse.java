package com.accounting.accountingrest.response;

import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.Expense;
import com.accounting.accountingrest.hibernate.model.Income;
import com.accounting.accountingrest.hibernate.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private int id;
    private String description;
    private String title;
    private int accountingSystemID;
    private int parentCategoryID;
    private List<IncomeResponse> incomes = new ArrayList<>();
    private List<ExpenseResponse> expenses = new ArrayList<>();
    private List<SubcategoryResponse> subcategories = new ArrayList<>();
    private List<UserResponse> responsibleUsers = new ArrayList<>();

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
        if(!category.getIncomes().isEmpty()){
            for(Income income: category.getIncomes()){
                incomes.add(new IncomeResponse(income));
            }
        }
        if(!category.getExpenses().isEmpty()){
            for(Expense expense: category.getExpenses()){
                expenses.add(new ExpenseResponse(expense));
            }
        }
        if(!category.getSubCategories().isEmpty()){
            for(Category subcategory: category.getSubCategories()){
                subcategories.add(new SubcategoryResponse(subcategory));
            }
        }

        if(!category.getResponsibleUsers().isEmpty()){
            for(User user: category.getResponsibleUsers())
                responsibleUsers.add(new UserResponse(user));
        }
    }
}
