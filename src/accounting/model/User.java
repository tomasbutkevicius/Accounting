package accounting.model;

import lombok.Data;

import java.io.Serializable;

public class User implements Serializable {
    private UserType type;
    private String name;
    private String password;
    private String contactInformation;

    public User(UserType type, String name, String password, String contactInformation) {
        this.type = type;
        this.name = name;
        this.password = password;
        this.contactInformation = contactInformation;
    }

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

    @Override
    public String toString() {
        return "\nUser{" + "username = '" + name + '\'' + ", user type = " + type +
                "} \n contact information: " + contactInformation + ", password = '" + password + "'\n";
    }
}
