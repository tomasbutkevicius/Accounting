package window;


import controller.UserController;
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
import model.User;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;
import service.UserService;

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
    public Button loginBtn;
    private AccountingSystem accountingSystem;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        if (accountingSystem != null)
            accountingSystem = accountingSystemHib.getById(accountingSystem.getId());
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        loadStartWindow();
    }


    private void loadStartWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();
        startWindow.setEntityManagerFactory(entityManagerFactory);
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        startWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void loginBtnClick(ActionEvent actionEvent) throws IOException {
        errorMessage.setText("");
        handleLogin();
    }

    private void handleLogin() throws IOException {
        User user =
                UserService.login(entityManagerFactory,
                        accountingSystem, usernameField.getText(), passwordField.getText());
        if (user == null) {
            errorMessage.setText("Invalid username or password");
        } else {
            System.out.println(user);
            loadAccounting(user);
        }
    }


    private void loadAccounting(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AccountingWindow.fxml"));
        Parent root = loader.load();
        AccountingWindow accounting = loader.getController();
        accounting.setAccountingSystem(accountingSystem);
        accounting.setEntityManagerFactory(entityManagerFactory);
        accounting.setUser(user);
        accounting.setCategoryList(accountingSystem);

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + user.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void signBtnClick(ActionEvent actionEvent) throws IOException {
        loadSignUpWindow();
    }

    private void loadSignUpWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/SignUpWindow.fxml"));
        Parent root = loader.load();
        SignUpWindow signUpWindow = loader.getController();

        signUpWindow.setAccountingSystem(accountingSystem);
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
}
