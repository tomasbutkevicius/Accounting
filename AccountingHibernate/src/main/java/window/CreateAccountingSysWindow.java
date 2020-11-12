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
import model.User;
import model.UserType;
import persistenceController.AccountingSystemHib;
import service.AccountingSystemService;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateAccountingSysWindow implements Initializable {
    public TextField systemNameField;
    public Label errorMessage;
    public Button backBtn;
    public TextField versionField;
    public TextField adminNameField;
    public TextField adminPasswordField;
    private EntityManagerFactory entityManagerFactory;
    private AccountingSystem accountingSystem;
    private AccountingSystemHib accountingSystemHib;


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

        if (accountingSystem != null)
            startWindow.setAccountingSystem(accountingSystem);

        startWindow.setAccountingSystemHib(accountingSystemHib);
        startWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void createBtnClick(ActionEvent actionEvent) throws IOException {
        AccountingSystem accountingSystem = createAccountingSystem();

        if (accountingSystem != null) {
            if(validAccountingSystem(accountingSystem))
            {
                User admin = new User(UserType.ADMIN, adminNameField.getText(), adminPasswordField.getText(), "First admin");
                AccountingSystemService.create(accountingSystemHib, accountingSystem, admin);
                this.accountingSystem = accountingSystemHib.getByName(accountingSystem.getName());
                errorMessage.setText("");
                Popup.display("Accounting system creation", "New accounting system created", "Okay");
                loadStartWindow();
            }
        }
    }

    private boolean validAccountingSystem(AccountingSystem accountingSystem) {
        if(accountingSystemHib.getByName(accountingSystem.getName()) != null) {
            errorMessage.setText("Accounting system with this name already exists");
            return false;
        }
        return true;
    }

    private AccountingSystem createAccountingSystem() {
        if(emptyField()){
            errorMessage.setText("Required fields are empty");
            return null;
        }
        if(hasSpaces()){
            errorMessage.setText("Admin information cannot have spaces");
            return null;
        }
        return new AccountingSystem(systemNameField.getText(), LocalDate.now(), versionField.getText(), 0, 0);
    }

    private boolean emptyField() {
        return versionField.getText().replaceAll("\\s", "").isEmpty()
                || systemNameField.getText().replaceAll("\\s", "").isEmpty()
                || adminNameField.getText().replaceAll("\\s", "").isEmpty()
                || adminPasswordField.getText().replaceAll("\\s", "").isEmpty();
    }

    private boolean hasSpaces() {
        if (adminPasswordField.getText().contains(" ") || adminNameField.getText().contains(" ")) return true;
        return false;
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
    }

    public AccountingSystemHib getAccountingSystemHib() {
        return accountingSystemHib;
    }

    public void setAccountingSystemHib(AccountingSystemHib accountingSystemHib) {
        this.accountingSystemHib = accountingSystemHib;
    }
}
