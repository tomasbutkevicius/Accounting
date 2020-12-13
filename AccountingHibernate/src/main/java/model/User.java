package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String password;
    private String contactInformation;
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private List<Category> categories;
    @ManyToOne
    private  AccountingSystem accountingSystem;

    public User(UserType type, String name, String password, String contactInformation, Category category) {
        this.type = type;
        this.name = name;
        this.password = password;
        this.contactInformation = contactInformation;
        this.categories.add(category);
    }

    public User(UserType type, String name, String password, String contactInformation) {
        this.type = type;
        this.name = name;
        this.password = password;
        this.contactInformation = contactInformation;
    }

    public User(int id, String name, UserType type, String password, String contactInformation, List<Category> categories, AccountingSystem accountingSystem) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.password = password;
        this.contactInformation = contactInformation;
        this.categories = categories;
        this.accountingSystem = accountingSystem;
    }

    public User(){}

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public String toString() {
        return "\nUser{" + "username = '" + name + '\'' + ", user type = " + type +
                "} \n contact information: " + contactInformation  + "\n";
    }
}
