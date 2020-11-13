import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AccountingSystem;
import model.Category;
import model.User;
import model.UserType;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import persistenceController.UserHibController;
import window.StartWindow;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//--------Create user
//        UserHibController userHibController = new UserHibController(factory);
//        userHibController.create(new User(UserType.ADMIN, "test", "test", "test"));
//--------Get user
//        List<User> responsibleUsers = new ArrayList<User>();
//        User user = userHibController.getByName("test");
//        responsibleUsers.add(user);
//--------Create category with responsible user
//        Category category = new Category("yes", "desc", responsibleUsers);
//--------Add to user object category
//        user.getCategories().add(category);
//        CategoryHibController categoryHibController = new CategoryHibController(factory);
//--------Save category to system
//        categoryHibController.create(category);
//

public class StartApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();
        startWindow.setEntityManagerFactory(entityManagerFactory);

        UserHibController userHibController = new UserHibController(entityManagerFactory);

        primaryStage.setTitle("Accounting System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
