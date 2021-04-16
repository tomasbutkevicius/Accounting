package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.hibernate.service.CategoryServiceHib;
import com.accounting.accountingrest.request.CategoryRequest;
import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.validator.CategoryServiceValidator;
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
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    private CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
    private CategoryServiceValidator categoryServiceValidator = new CategoryServiceValidator(userHibController, accountingSystemHib, categoryHibController);


    @Autowired
    public CategoryService() {
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryHibController.getCategoryList();
        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(new CategoryResponse(category));
        }
        return responseList;
    }

    public String createCategory(final CategoryRequest categoryRequest) {
        categoryServiceValidator.validateCreate(categoryRequest);
        AccountingSystem accountingSystem = accountingSystemHib.getById(categoryRequest.getAccountingSystemID());

        Category category = new Category(categoryRequest.getTitle(), categoryRequest.getDescription());
        if (categoryRequest.getParentCategoryID() == 0) {
            return CategoryServiceHib.create(entityManagerFactory, accountingSystem, category);
        } else {
            Category parentCategory = categoryHibController.getById(categoryRequest.getParentCategoryID());
            category.setParentCategory(parentCategory);
            return CategoryServiceHib.createSubCategory(entityManagerFactory, accountingSystem, category);
        }
    }


    public String updateCategory(CategoryRequest categoryRequest, int id) {
        categoryServiceValidator.validateUpdate(id, categoryRequest);
        Category category = categoryHibController.getById(id);

        category.setDescription(categoryRequest.getDescription());
        category.setTitle(categoryRequest.getTitle());
        categoryHibController.update(category);

        return "Category is updated";
    }

    public void deleteCategory(int id) {
        categoryServiceValidator.validateFind(id);
        categoryHibController.delete(id);
    }

    public CategoryResponse findCategory(int id) {
        categoryServiceValidator.validateFind(id);

        return new CategoryResponse(categoryHibController.getById(id));
    }


    public List<CategoryResponse> findUserCategories(int id) {
        User user = userHibController.getById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        List<CategoryResponse> assignedCategoryList = getUserAssignedCategories(user);
        return assignedCategoryList;
    }

    private List<CategoryResponse> getUserAssignedCategories(User user) {
        List<Category> allCategories = categoryHibController.getCategoryList();

        List<CategoryResponse> responseList = new ArrayList<>();
        for (Category category : allCategories) {
            if (category.getResponsibleUsers().stream()
                    .filter(respUser -> String.valueOf(respUser.getId()).equals(String.valueOf(user.getId())))
                    .findFirst()
                    .orElse(null) != null)
                responseList.add(new CategoryResponse(category));
        }
        return responseList;
    }

    public List<CategoryResponse> findParentCategories() {
        List<Category> categories = categoryHibController.getCategoryList();
        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            if (category.getParentCategory() == null) {
                responseList.add(new CategoryResponse(category));
            }
        }
        return responseList;
    }

    public List<CategoryResponse> getCategoriesInSystem(String id) {
        AccountingSystem accountingSystem = accountingSystemHib.getById(Integer.parseInt(id));

        if (accountingSystem == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");

        List<Category> categories = CategoryServiceHib.getAllCategoriesInSystem(entityManagerFactory, accountingSystem);

        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(new CategoryResponse(category));
        }
        return responseList;
    }

    public void addUser(int catID, int userID) throws Exception {
        Category category = categoryHibController.getById(catID);

        UserHibController userHibController = new UserHibController(entityManagerFactory);
        User user = userHibController.getById(userID);

        categoryServiceValidator.validateAddUserToCategory(category, user);
        CategoryServiceHib.addResponsibleUser(category, user, entityManagerFactory);
    }


    public void removeUserFromCategory(int catID, int userID) {
        categoryServiceValidator.validateRemoveUserFromCategory(catID, userID);
        categoryHibController.removeUserFromCategory(catID, userID);
    }

    public String findCategoryTitle(int parseInt) {
        if (categoryHibController.getById(parseInt) == null) {
            return "";
        }

        return categoryHibController.getById(parseInt).getTitle();
    }
}
