package com.accounting.accountingrest.controller;

import com.accounting.accountingrest.request.CategoryRequest;
import com.accounting.accountingrest.response.CategoryResponse;
import com.accounting.accountingrest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable String id){
        return categoryService.findCategory(Integer.parseInt(id));
    }

    @GetMapping("/get_name/{id}")
    public String getCategoryTitle(@PathVariable String id){
        return categoryService.findCategoryTitle(Integer.parseInt(id));
    }

    @GetMapping("/parents")
    public List<CategoryResponse> getParentCategories(){
        return categoryService.findParentCategories();
    }

    @PostMapping
    ResponseEntity<HttpStatus> createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping("/user/{id}")
    public List<CategoryResponse> getUserCategories(@PathVariable String id){
        return categoryService.findUserCategories(Integer.parseInt(id));
    }

    @PostMapping("/{catID}/user/{userID}")
    ResponseEntity<HttpStatus> addResponsibleUser(@PathVariable int catID, @PathVariable int userID) throws Exception {
        categoryService.addUser(catID, userID);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable String id){
        int idNum = Integer.parseInt(id);
        categoryService.updateCategory(categoryRequest, idNum);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/{catID}/user/{userID}")
    public void deleteUserFromCategory(@PathVariable int catID, @PathVariable int userID) throws Exception {
        categoryService.removeUserFromCategory(catID, userID);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable String id){
        int idNum = Integer.parseInt(id);
        categoryService.deleteCategory(idNum);
    }

}
