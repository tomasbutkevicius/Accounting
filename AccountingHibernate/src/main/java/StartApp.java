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
        CategoryHibController categoryHibController = new CategoryHibController(factory);
        UserHibController userHibController = new UserHibController(factory);

        System.out.println(userHibController.getByName("test"));

        List<User> users = new ArrayList<User>();
        Category category = new Category("test", "descr", users);

        category.getResponsibleUsers().add(userHibController.getByName("test"));
        User user = userHibController.getByName("test");
        user.getCategories().add(category);
        categoryHibController.create(category);
        userHibController.update(user);

    }
}
