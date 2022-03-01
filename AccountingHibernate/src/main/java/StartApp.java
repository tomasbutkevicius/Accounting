import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import persistenceController.AccountingSystemHib;
import service.AccountingSystemService;
import window.StartWindow;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class StartApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manoDuombaze");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();
        startWindow.setEntityManagerFactory(entityManagerFactory);

        primaryStage.setTitle("Accounting System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void setUpSystem(EntityManagerFactory entityManagerFactory){
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        AccountingSystem accountingSystem = accountingSystemHib.getByName("System");

        if (accountingSystem == null) {
            accountingSystem = new AccountingSystem("System", LocalDate.now(), "v1", 0, 0);
            User admin = new User(UserType.ADMIN, "admin", "password", "Karaganda");
            AccountingSystemService.create(accountingSystemHib, accountingSystem, admin);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
