package accounting.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class Category implements Serializable {
    private String title;
    private String description;
    private ArrayList<User> responsibleUsers;
    private Category parentCategory;
    private ArrayList<Category> subCategories = new ArrayList<>();
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Income> incomes = new ArrayList<>();

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

}
