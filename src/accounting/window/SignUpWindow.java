package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.controller.UserController;
import accounting.model.AccountingSystem;
import accounting.model.User;
import accounting.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpWindow implements Initializable {
  private AccountingSystem accountingSystem;
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
    radioBtnCompany.setToggleGroup(group);
    radioBtnPrivate.setToggleGroup(group);
  }

  public void signBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
    User user = createUser();
    if (user != null) {
      errorMessage.setText("");
      Popup.display("User creation", AccountingSystemController.addUser(accountingSystem, user), "Okay");
    }
  }

  private void loadLoginWindow() throws IOException, ClassNotFoundException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
    Parent root = loader.load();
    LoginWindow login = loader.getController();
    login.setAccountingSystem(accountingSystem);

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
}
