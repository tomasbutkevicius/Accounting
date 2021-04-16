package com.accounting.accountingrest.validator;

import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.model.UserType;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.request.CategoryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class CategoryServiceValidator {
    private UserHibController userHibController;
    private AccountingSystemHib accountingSystemHib;
    private CategoryHibController categoryHibController;

    public CategoryServiceValidator(UserHibController userHibController, AccountingSystemHib accountingSystemHib, CategoryHibController categoryHibController){
        this.userHibController = userHibController;
        this.accountingSystemHib = accountingSystemHib;
        this.categoryHibController = categoryHibController;
    }

    public void validateFind(int id) {
        Category category = categoryHibController.getById(id);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
    }

    public void validateCreate(CategoryRequest categoryRequest) {
        if (categoryRequest.getTitle() == null || categoryRequest.getDescription() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");


        AccountingSystem accountingSystem = accountingSystemHib.getById(categoryRequest.getAccountingSystemID());
        if (accountingSystem == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");
        }

        if (categoryRequest.getParentCategoryID() != 0) {
            Category parentCategory = categoryHibController.getById(categoryRequest.getParentCategoryID());
            if (parentCategory == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
    }

    public void validateUpdate(int id, CategoryRequest categoryRequest) {
        Category category = categoryHibController.getById(id);
        if (category == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters");

        if(categoryRequest.getTitle() == null || categoryRequest.getDescription() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters");
    }

    public void validateAddUserToCategory(Category category, User user) {
        if (category == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

        if (user.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Admin cannot be responsible");

        for (User responsibleUser : category.getResponsibleUsers()) {
            if (responsibleUser.getId() == user.getId())
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User already exists");
        }

        if (user.getAccountingSystem().getId() != category.getAccountingSystem().getId())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "System must be same");
    }

    public void validateRemoveUserFromCategory(int catID, int userID){
        if (categoryHibController.getById(catID) == null || userHibController.getById(userID) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User or category not found");
        }
    }

}
