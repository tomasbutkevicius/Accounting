package accounting.window;

import accounting.ObjectIO;
import accounting.model.AccountingSystem;
import accounting.model.User;
import accounting.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
    public Button loadSysBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createAccountingSystem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

    }

    private void loadLoginWindow() throws IOException, ClassNotFoundException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = loader.load();
            AccountingSystem accountingSystem = createTemporarySystem();
//            ObjectIO.readObjectFromFile(accountingSystem);
            LoginWindow login = loader.getController();
            login.setAccountingSystem(accountingSystem);

            Stage stage = (Stage) loadSysBtn.getScene().getWindow();
            stage.setTitle("Accounting System");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
    }

    private AccountingSystem createTemporarySystem() {
        AccountingSystem accountingSystem = new AccountingSystem("Demo", LocalDate.now(), "v1", 0, 0);
        User tempUser = new User(UserType.INDIVIDUAL, "Tomas", "password", "Vilnius");
        accountingSystem.getUsers().add(tempUser);
        return accountingSystem;
    }

    public void loadAccountingSystem(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        loadLoginWindow();
    }
}
