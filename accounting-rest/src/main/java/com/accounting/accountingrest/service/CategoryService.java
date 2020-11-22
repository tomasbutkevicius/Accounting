package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.service.CategoryServiceHib;
import com.accounting.accountingrest.request.AccountingSystemRequest;
import com.accounting.accountingrest.request.CategoryRequest;
import com.accounting.accountingrest.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");

    @Autowired
    public CategoryService() {
    }

    public List<CategoryResponse> findAll() {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        List<Category> categories = categoryHibController.getCategoryList();
        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(new CategoryResponse(category));
        }
        return responseList;
    }

    public String createCategory(final CategoryRequest categoryRequest) {
        if(categoryRequest.getTitle() == null || categoryRequest.getDescription() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing parameters");

        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(categoryRequest.getAccountingSystemID());
        if (accountingSystem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounting system not found");
        }

        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = new Category(categoryRequest.getTitle(), categoryRequest.getDescription());
        if (categoryRequest.getParentCategoryID() == 0) {
            return CategoryServiceHib.create(entityManagerFactory, accountingSystem, category);
        } else {
            System.out.println(categoryRequest.getParentCategoryID());
            Category parentCategory = categoryHibController.getById(categoryRequest.getParentCategoryID());
            if (parentCategory == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");

            category.setParentCategory(parentCategory);
            return CategoryServiceHib.createSubCategory(entityManagerFactory, accountingSystem, category);
        }
    }

    public String updateCategory(CategoryRequest categoryRequest, int id) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(id);
        if(categoryRequest.getTitle() == null || categoryRequest.getDescription() == null || category == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid parameters");

        category.setDescription(categoryRequest.getDescription());
        category.setTitle(categoryRequest.getTitle());

        return categoryHibController.update(category);
    }
}
