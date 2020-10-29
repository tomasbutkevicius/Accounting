package accounting.window;

import accounting.ObjectIO;
import accounting.controller.AccountingSystemController;
import accounting.model.AccountingSystem;
import accounting.model.User;
import accounting.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
  public Button startSysBtn;
  public Label messageToUser;
  public Button createDemoBtn;
  public AccountingSystem accountingSystem;
  public Label systemInformation;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void createAccountingSystem(ActionEvent actionEvent)
      throws IOException, ClassNotFoundException {
    popUpConfirmCreateSystem();
  }

  private void popUpConfirmCreateSystem() {
    messageToUser.setText("");
    Stage popUpWindow = new Stage();

    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle("Create system prompt");

    Label question =
        new Label("Are you sure you want to create new system? You will lose your current data.");
    Button backBtn = new Button("Go back");
    Button acceptBtn = new Button("Create new system");
    backBtn.setOnAction(e -> popUpWindow.close());
    acceptBtn.setOnAction(
        e -> {
          try {
            loadCreateAccountingSystemWindow();
            popUpWindow.close();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
    VBox layout = new VBox(10);

    layout.getChildren().addAll(question, backBtn, acceptBtn);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 500, 300);

    popUpWindow.setScene(scene1);

    popUpWindow.showAndWait();
  }

  private void loadCreateAccountingSystemWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountingSystem.fxml"));
    Parent root = loader.load();
    //            ObjectIO.readObjectFromFile(accountingSystem);
    CreateAccountingSystem createAccountingSystem = loader.getController();
    Stage stage = (Stage) startSysBtn.getScene().getWindow();
    stage.setTitle("Accounting System creation");
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
    if (accountingSystem != null) {
      setSystemInformation();
    }
  }

  private void setSystemInformation() {
    systemInformation.setText(
        "Loaded system: "
            + accountingSystem.getName()
            + ".\n System version: "
            + accountingSystem.getSystemVersion()
            + "\n Creation date: "
            + accountingSystem.getSystemCreationDate());
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

  public void loadBtnClick(ActionEvent actionEvent) {
    messageToUser.setText("");
    Stage popUpWindow = new Stage();

    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle("Load save");

    Label fileQuest = new Label("Enter file name (lastSave.txt):");
    TextField fileField = new TextField();

    Button backBtn = new Button("Back");
    Button loadBtn = new Button("Load");
    backBtn.setOnAction(e -> popUpWindow.close());
    loadBtn.setOnAction(
        e -> {
          try {
            loadSystemFromFile(fileField.getText());
          } catch (IOException ex) {
            ex.printStackTrace();
          } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
          }
        });
    VBox layout = new VBox(10);

    layout.getChildren().addAll(fileQuest, fileField, backBtn, loadBtn);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 500, 300);

    popUpWindow.setScene(scene1);

    popUpWindow.showAndWait();
  }

  private void loadSystemFromFile(String file) throws IOException, ClassNotFoundException {
    File f = new File(file);
    if (!f.exists()) {
      Popup.display("Error", "File not found", "Okay");
    } else {
      accountingSystem = ObjectIO.readObjectFromFile(accountingSystem, file);
      Popup.display("Loaded", "System loaded", "Okay");
      setSystemInformation();
    }
  }
}
