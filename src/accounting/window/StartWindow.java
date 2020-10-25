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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
  public Button startSysBtn;
  public Label messageToUser;
  public Button createDemoBtn;
  public AccountingSystem accountingSystem;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void createAccountingSystem(ActionEvent actionEvent)
      throws IOException, ClassNotFoundException {}

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
  }

  private void loadLoginWindow() throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
    Parent root = loader.load();
    //            ObjectIO.readObjectFromFile(accountingSystem);
    LoginWindow login = loader.getController();
    login.setAccountingSystem(accountingSystem);

    Stage stage = (Stage) startSysBtn.getScene().getWindow();
    stage.setTitle("Accounting System");
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  public void createDemoBtnClick(ActionEvent actionEvent) {
    accountingSystem = createTemporarySystem();
  }

  private AccountingSystem createTemporarySystem() {
    AccountingSystem accountingSystem = new AccountingSystem("Demo", LocalDate.now(), "v1", 0, 0);
    User tempUser = new User(UserType.PRIVATE, "Tomas", "password", "Vilnius");
    accountingSystem.getUsers().add(tempUser);
    messageToUser.setText("Created demo system (temp user Tomas, password= password)");
    return accountingSystem;
  }

  public void startAccountingSystem(ActionEvent actionEvent)
      throws IOException, ClassNotFoundException {
    messageToUser.setText("");
    if (accountingSystem == null) {
      messageToUser.setText("First load or create a system");
    } else {
      loadLoginWindow();
    }
  }
}
