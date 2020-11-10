package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    private String name;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String password;
    private String contactInformation;
    @ManyToMany(cascade = CascadeType.MERGE)
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
