import model.Category;
import model.User;
import model.UserType;
import persistenceController.CategoryHibController;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class StartApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("accounting_hib");
        UserHibController userHibController = new UserHibController(factory);

        CategoryHibController categoryHibController = new CategoryHibController(factory);

//        userHibController.create(new User(UserType.ADMIN, "test", "test", "test"));
        List<User> responsibleUsers = new ArrayList<User>();
        responsibleUsers.add(userHibController.getByName("test"));
//        Category category = new Category("yes", "desc", responsibleUsers);
        User user = userHibController.getByName("test");
        user.setName("What");
        userHibController.update(user);

//        categoryHibController.create(new Category("yes", "desc", responsibleUsers));
    }
}
