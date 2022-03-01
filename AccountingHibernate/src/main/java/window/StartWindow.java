package window;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import model.AccountingSystem;
import model.User;
import model.UserType;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
    public Button loadBtn;
    public Label systemInformation;
    public Label messageToUser;
    public Button startSysBtn;
    private AccountingSystem accountingSystem;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        if (accountingSystem != null)
            accountingSystem = accountingSystemHib.getById(accountingSystem.getId());
    }


    private void loadLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginWindow.fxml"));
        Parent root = loader.load();
        LoginWindow loginWindow = loader.getController();
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        loginWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        loginWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) startSysBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void startAccountingSystem(ActionEvent actionEvent) throws IOException {
        messageToUser.setText("");
        if (accountingSystem == null) {
            messageToUser.setText("First select or create accounting system");
        } else {
            loadLoginWindow();
        }
    }

    public void createSysBtnClick(ActionEvent actionEvent) throws IOException {
        loadCreateAccountingSystem();
    }

    private void loadCreateAccountingSystem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/CreateAccountingSystem.fxml"));
        Parent root = loader.load();
        CreateAccountingSysWindow createAccountingSystem = loader.getController();

        if (accountingSystem != null) {
            AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
            createAccountingSystem.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        }
        createAccountingSystem.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) loadBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }


    public void createDemoBtnClick(ActionEvent actionEvent) {
    }

    public void loadBtnClick(ActionEvent actionEvent) throws IOException {
        loadDatabaseWindow();
    }

    private void loadDatabaseWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/DatabasesWindow.fxml"));
        Parent root = loader.load();
        DatabasesWindow databasesWindow = loader.getController();
        if (accountingSystem != null){
            AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
            databasesWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        }
        databasesWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) loadBtn.getScene().getWindow();
        stage.setTitle("Accounting System databases ");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
        setSystemInformation();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    private void setSystemInformation() {
        systemInformation.setText(
                "Loaded system: "
                        + accountingSystem.getName()
                        + ".\n System version: "
                        + accountingSystem.getSystemVersion()
                        + "\n Creation date: "
                        + accountingSystem.getSystemCreationDate());
    }

}
