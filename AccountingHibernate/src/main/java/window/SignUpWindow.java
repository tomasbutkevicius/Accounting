package window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AccountingSystem;
import model.User;
import model.UserType;
import persistenceController.AccountingSystemHib;
import persistenceController.UserHibController;
import service.UserService;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpWindow implements Initializable {
    private AccountingSystem accountingSystem;
    private EntityManagerFactory entityManagerFactory;
    @FXML
    public RadioButton radioBtnPrivate;
    public RadioButton radioBtnCompany;
    final ToggleGroup group = new ToggleGroup();
    public TextField usernameField;
    public TextField passwordField;
    public TextArea contactField;
    public Button singBtn;
    public Label errorMessage;
    public Button backBtn;

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        if (accountingSystem != null)
            accountingSystem = accountingSystemHib.getById(accountingSystem.getId());

        radioBtnCompany.setToggleGroup(group);
        radioBtnPrivate.setToggleGroup(group);
    }

    public void signBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        User user = createUser();
        if (user != null) {
            if(validUser(user)){
                errorMessage.setText("");
                Popup.display("User creation", UserService.create(entityManagerFactory, accountingSystem, user), "Okay");
            } else {
                errorMessage.setText("User with this name already exists");
            }
        }
    }

    private boolean validUser(User user) {
        for(User userInSystem: accountingSystem.getUsers()){
            if(userInSystem.getName().equals(user.getName())) return false;
        }
        return true;
    }

    private void loadLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginWindow.fxml"));
        Parent root = loader.load();
        LoginWindow loginWindow = loader.getController();
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        loginWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        loginWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private User createUser() {
        if (typeNotSelected()) {
            errorMessage.setText("User must have a type");
            return null;
        }

        if (hasSpaces()) {
            errorMessage.setText("Username and password \n cannot have spaces");
            return null;
        }
        if (emptyField()) {
            errorMessage.setText("Missing required information");
            return null;
        }

        UserType type = UserType.COMPANY;
        if (radioBtnPrivate.isSelected()) {
            type = UserType.PRIVATE;
        }
        return new User(type, usernameField.getText(), passwordField.getText(), contactField.getText());
    }

    private boolean typeNotSelected() {
        return !radioBtnPrivate.isSelected() && !radioBtnCompany.isSelected();
    }

    private boolean emptyField() {
        return usernameField.getText().replaceAll("\\s", "").isEmpty()
                || passwordField.getText().replaceAll("\\s", "").isEmpty()
                || contactField.getText().replaceAll("\\s", "").isEmpty();
    }

    private boolean hasSpaces() {
        if (usernameField.getText().contains(" ") || passwordField.getText().contains(" ")) return true;
        return false;
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        loadLoginWindow();
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
