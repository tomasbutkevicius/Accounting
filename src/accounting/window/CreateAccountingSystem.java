package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.model.AccountingSystem;
import accounting.model.User;
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
            errorMessage.setText("");
            Popup.display("Accounting system creation", "New accounting system created", "Okay");
            loadStartWindow();
        }
    }

    private AccountingSystem createAccountingSystem() {
        if(emptyField()){
            errorMessage.setText("Required fields are empty");
            return null;
        } else {
            return new AccountingSystem(systemNameField.getText(), LocalDate.now(), versionField.getText(), 0, 0);
        }
    }

    private boolean emptyField() {
        return versionField.getText().replaceAll("\\s", "").isEmpty()
                || systemNameField.getText().replaceAll("\\s", "").isEmpty();
    }
}
