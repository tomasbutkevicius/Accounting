package com.accounting.accountingrest.service;

import com.accounting.accountingrest.hibernate.model.User;
import com.accounting.accountingrest.hibernate.model.UserType;
import com.accounting.accountingrest.hibernate.repository.AccountingSystemHib;
import com.accounting.accountingrest.hibernate.repository.CategoryHibController;
import com.accounting.accountingrest.hibernate.model.AccountingSystem;
import com.accounting.accountingrest.hibernate.model.Category;
import com.accounting.accountingrest.hibernate.repository.UserHibController;
import com.accounting.accountingrest.hibernate.service.CategoryServiceHib;
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
        if (!validCategoryRequest(categoryRequest))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");

        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getById(categoryRequest.getAccountingSystemID());
        if (accountingSystem == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounting system not found");
        }

        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = new Category(categoryRequest.getTitle(), categoryRequest.getDescription());
        if (categoryRequest.getParentCategoryID() == 0) {
            return CategoryServiceHib.create(entityManagerFactory, accountingSystem, category);
        } else {
            System.out.println(categoryRequest.getParentCategoryID());
            Category parentCategory = categoryHibController.getById(categoryRequest.getParentCategoryID());
            if (parentCategory == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");

            category.setParentCategory(parentCategory);
            return CategoryServiceHib.createSubCategory(entityManagerFactory, accountingSystem, category);
        }
    }


    public String updateCategory(CategoryRequest categoryRequest, int id) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(id);
        if (validCategoryRequest(categoryRequest) || category == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters");

        category.setDescription(categoryRequest.getDescription());
        category.setTitle(categoryRequest.getTitle());
        categoryHibController.update(category);

        return "Category is updated";
    }

    public void deleteCategory(int id) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(id);
        if (category == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        categoryHibController.delete(id);
    }

    public CategoryResponse findCategory(int id) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(id);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }

        return new CategoryResponse(category);
    }


    public List<CategoryResponse> findUserCategories(int id) {
        UserHibController userHibController = new UserHibController(entityManagerFactory);
        User user = userHibController.getById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        List<CategoryResponse> assignedCategoryList = getUserAssignedCategories(user);
        return assignedCategoryList;
    }

    private List<CategoryResponse> getUserAssignedCategories(User user) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
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
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
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
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
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
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        Category category = categoryHibController.getById(catID);

        UserHibController userHibController = new UserHibController(entityManagerFactory);
        User user = userHibController.getById(userID);

        validateAddUserToCategory(category, user);
        CategoryServiceHib.addResponsibleUser(category, user, entityManagerFactory);
    }


    public void removeUserFromCategory(int catID, int userID) throws Exception {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        if (categoryHibController.getById(catID) == null || new UserHibController(entityManagerFactory).getById(userID) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User or category not found");
        }
        categoryHibController.removeUserFromCategory(catID, userID);
    }

    public String findCategoryTitle(int parseInt) {
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        if (categoryHibController.getById(parseInt) == null) {
            return "";
        }

        return categoryHibController.getById(parseInt).getTitle();
    }

    private boolean validCategoryRequest(CategoryRequest categoryRequest) {
        return (categoryRequest.getTitle() != null && categoryRequest.getDescription() != null);
    }

    private void validateAddUserToCategory(Category category, User user) {
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
}
