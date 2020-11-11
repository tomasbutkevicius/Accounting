package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String password;
    private String contactInformation;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories;

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

    public User(int id, String name, UserType type, String password, String contactInformation, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.password = password;
        this.contactInformation = contactInformation;
        this.categories = categories;
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

    @Override
    public String toString() {
        return "\nUser{" + "username = '" + name + '\'' + ", user type = " + type +
                "} \n contact information: " + contactInformation + ", password = '" + password + "'\n";
    }
}
