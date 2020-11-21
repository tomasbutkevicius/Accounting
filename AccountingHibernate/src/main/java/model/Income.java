package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Income implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer amount;
    @ManyToOne
    private Category category;

    public Income(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public Income(String name, Integer amount, Category category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Income(int id, String name, Integer amount, Category category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }



    public Income() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
