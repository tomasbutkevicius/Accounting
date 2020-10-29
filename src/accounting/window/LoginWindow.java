package accounting.window;

import accounting.controller.UserController;
import accounting.model.AccountingSystem;
import accounting.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {

  public Button loginBtn;
  public Button backBtn;
  private AccountingSystem accountingSystem;
  @FXML public TextField usernameField;
  public TextField passwordField;
  public Label errorMessage;
  public Button signBtn;
  public Label systemNameField;

  public void setAccountingSystem(AccountingSystem accountingSystem) {
      this.accountingSystem = accountingSystem;
      systemNameField.setText(accountingSystem.getName());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void sign(ActionEvent actionEvent) throws IOException {
    loadSignUpWindow();
  }

  public void login(ActionEvent actionEvent) throws IOException {
    errorMessage.setText("");
    handleLogin();
  }

  private void handleLogin() throws IOException {
    User user =
        UserController.getUserByLogin(
            accountingSystem, usernameField.getText(), passwordField.getText());
    if (user == null) {
      errorMessage.setText("Invalid username or password");
    } else {
      loadAccounting(user);
    }
  }

  private void loadAccounting(User user) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountingWindow.fxml"));
    Parent root = loader.load();
    AccountingWindow accounting = loader.getController();
    accounting.setAccountingSystem(accountingSystem);
    accounting.setUser(user);
    accounting.setCategoryList(accountingSystem);

    Stage stage = (Stage) loginBtn.getScene().getWindow();
    stage.setTitle("Accounting System. User " + user.getName());
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  private void loadSignUpWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));
    Parent root = loader.load();
    SignUpWindow signUp = loader.getController();
    signUp.setAccountingSystem(accountingSystem);

    Stage stage = (Stage) signBtn.getScene().getWindow();
    stage.setTitle("Accounting System");
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  public void goBack(ActionEvent actionEvent) throws IOException {
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
}
