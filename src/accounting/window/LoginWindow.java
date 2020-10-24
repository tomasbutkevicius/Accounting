package accounting.window;

import accounting.model.AccountingSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {

  private AccountingSystem accountingSystem;
  @FXML public TextField signUsernameField;
  public TextField signPasswordField;
  public PasswordField loginPasswordField;
  public TextField loginUsernameField;
  public Label systemNameField;

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
    systemNameField.setText(accountingSystem.getName());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void sign(ActionEvent actionEvent) {}

  public void login(ActionEvent actionEvent) {}
}
