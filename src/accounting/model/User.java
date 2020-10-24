package accounting.model;

import lombok.Data;

import java.io.Serializable;

@Data
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

    @Override
    public String toString() {
        return "\nUser{" + "username = '" + name + '\'' + ", user type = " + type +
                "} \n contact information: " + contactInformation + ", password = '" + password + "'\n";
    }
}
