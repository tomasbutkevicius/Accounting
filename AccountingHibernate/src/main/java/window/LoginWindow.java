package window;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AccountingSystem;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {
    public Button backBtn;
    public Label errorMessage;
    public TextField passwordField;
    public TextField usernameField;
    public Label systemNameField;
    private EntityManagerFactory entityManagerFactory;
    private AccountingSystem accountingSystem;
    private AccountingSystemHib accountingSystemHib;
    private UserHibController userHibController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        loadStartWindow();
    }


    private void loadStartWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();

        startWindow.setAccountingSystem(accountingSystem);
        startWindow.setAccountingSystemHib(accountingSystemHib);
        startWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void loginBtnClick(ActionEvent actionEvent) {
    }

    public void signBtnClick(ActionEvent actionEvent) throws IOException {
        loadSignUpWindow();
    }

    private void loadSignUpWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/SignUpWindow.fxml"));
        Parent root = loader.load();
        SignUpWindow signUpWindow = loader.getController();

        signUpWindow.setAccountingSystem(accountingSystem);
        signUpWindow.setAccountingSystemHib(accountingSystemHib);
        signUpWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
        systemNameField.setText(accountingSystem.getName());
    }

    public AccountingSystemHib getAccountingSystemHib() {
        return accountingSystemHib;
    }

    public void setAccountingSystemHib(AccountingSystemHib accountingSystemHib) {
        this.accountingSystemHib = accountingSystemHib;
    }

    public UserHibController getUserHibController() {
        return userHibController;
    }

    public void setUserHibController(UserHibController userHibController) {
        this.userHibController = userHibController;
    }
}
