package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "categories")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> responsibleUsers;
    @ManyToOne
    private Category parentCategory;
    @OneToMany
    private List<Category> subCategories = new ArrayList<>();

    public Category(String title, String description, ArrayList<User> responsibleUsers) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
    }

    public Category(String title, String description, ArrayList<User> responsibleUsers, Category parentCategory) {
        this.title = title;
        this.description = description;
        this.responsibleUsers = responsibleUsers;
        this.parentCategory = parentCategory;
    }

    public Category() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(ArrayList<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
