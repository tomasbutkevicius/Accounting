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

        userHibController.create(new User(UserType.ADMIN, "test", "test", "test"));
        User user = userHibController.getByName("test");
        List<User> responsibleUsers = new ArrayList<User>();
        responsibleUsers.add(user);
        Category category = new Category("yes", "desc", responsibleUsers);
        user.getCategories().add(category);
        userHibController.update(user);
    }
}
