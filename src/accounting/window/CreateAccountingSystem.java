package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.controller.UserController;
import accounting.model.AccountingSystem;
import accounting.model.User;
import accounting.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CreateAccountingSystem {
    public TextField systemNameField;
    public Label errorMessage;
    public Button backBtn;
    public TextField versionField;
    public TextField adminNameField;
    public TextField adminPasswordField;
    private AccountingSystem accountingSystem;

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        loadStartWindow();
    }

    private void loadStartWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();
        startWindow.setAccountingSystem(accountingSystem);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void createBtnClick(ActionEvent actionEvent) throws IOException {
        accountingSystem = createAccountingSystem();
        if (accountingSystem != null) {
            User admin = new User(UserType.ADMIN, adminNameField.getText(), adminPasswordField.getText(), "First admin");
            AccountingSystemController.addUser(accountingSystem, admin);
            errorMessage.setText("");
            Popup.display("Accounting system creation", "New accounting system created", "Okay");
            loadStartWindow();
        }
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
}
