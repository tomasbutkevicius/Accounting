package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.controller.CategoryController;
import accounting.controller.UserController;
import accounting.model.AccountingSystem;
import accounting.model.Category;
import accounting.model.User;
import accounting.model.UserType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountingWindow implements Initializable {
  public Button backBtn;
  public Label systemNameField;
  public Button manageCatBtn;
  public Button contactBtn;
  public TextField usernameField;
  public TextField userPasswordField;
  public TextField userContactField;
  public Button updateUserBtn;
  public Button deleteUserBtn;
  public Label systemIncomeField;
  public Label systemExpenseField;
  public Label systemDateField;
  public Label messageToUser;
  public RadioButton radioBtnPrivate;
  public RadioButton radioBtnCompany;
  final ToggleGroup group = new ToggleGroup();
  public ListView categoryList;
  public Button addCatBtn;
  public TextField editCatNameField;
  public Label errorMessage;
  private AccountingSystem accountingSystem;
  private User activeUser;

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
    systemNameField.setText(accountingSystem.getName());
    systemDateField.setText(accountingSystem.getSystemCreationDate().toString());
    systemIncomeField.setText(Integer.toString(accountingSystem.getIncome()));
    systemExpenseField.setText(Integer.toString(accountingSystem.getExpense()));
  }

  public void setCategoryList(AccountingSystem accountingSystem) {
    for (Category category : accountingSystem.getCategories()) {
      categoryList.getItems().add("'" + category.getTitle() + "'");
    }
  }

  public void setUser(User user) {
    this.activeUser = user;
    usernameField.setText(user.getName());
    userPasswordField.setText(user.getPassword());
    if (user.getType() == UserType.PRIVATE) {
      radioBtnPrivate.setSelected(true);
    } else {
      radioBtnCompany.setSelected(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    radioBtnCompany.setToggleGroup(group);
    radioBtnPrivate.setToggleGroup(group);
  }

  public void showUsersBtnClick(ActionEvent actionEvent) {
    errorMessage.setText("");
    loadUserList();
  }

  public void backBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
    loadLoginWindow();
  }

  public void manageCatBtnClick(ActionEvent actionEvent) throws IOException {
    Category category =
        AccountingSystemController.getCategoryByTitle(accountingSystem, editCatNameField.getText());
    if (category == null) {
      errorMessage.setText("Category not found");
    } else {
      loadManageCategoryWindow(category);
    }
  }

  private void loadManageCategoryWindow(Category category) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCategoryWindow.fxml"));
    Parent root = loader.load();
    ManageCategoryWindow manageCategoryWindow = loader.getController();
    manageCategoryWindow.setDisplayInformation(accountingSystem, category, activeUser);

    Stage stage = (Stage) manageCatBtn.getScene().getWindow();
    stage.setTitle("Accounting System. User " + activeUser.getName());
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  public void addCatBtnClick(ActionEvent actionEvent) throws IOException {
    loadCreateCategoryWindow();
  }

  public void contactBtnClick(ActionEvent actionEvent) {
    messageToUser.setText("");
    errorMessage.setText("");
    Stage popUpWindow = new Stage();

    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle("User contact information");

    userContactField = new TextField(activeUser.getContactInformation());

    Button backBtn = new Button("Back");
    Button updateContactBtn = new Button("Update");
    backBtn.setOnAction(e -> popUpWindow.close());
    updateContactBtn.setOnAction(e -> updateContactInformation(userContactField.getText()));
    VBox layout = new VBox(10);

    layout.getChildren().addAll(userContactField, backBtn, updateContactBtn);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 500, 300);

    popUpWindow.setScene(scene1);

    popUpWindow.showAndWait();
  }

  private void loadCreateCategoryWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCategoryWindow.fxml"));
    Parent root = loader.load();
    CreateCategoryWindow createCategoryWindow = loader.getController();
    createCategoryWindow.setAccountingSystem(accountingSystem);
    createCategoryWindow.setActiveUser(activeUser);

    Stage stage = (Stage) backBtn.getScene().getWindow();
    stage.setTitle("Accounting System. User " + activeUser.getName());
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
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

  private void updateContactInformation(String contactInformation) {
    if (!emptyField(contactInformation)) {
      activeUser.setContactInformation(contactInformation);
      messageToUser.setText("User contact info updated");
    }
  }

  private boolean emptyField(String text) {
    return text.replaceAll("\\s", "").isEmpty();
  }

  public void updateUserBtnClick(ActionEvent actionEvent) {
    messageToUser.setText("");
    User updatedUser = getUpdateUser();
    if (updatedUser != null) {
      AccountingSystemController.removeUser(accountingSystem, activeUser);
      if(UserController.getUserByName(accountingSystem, updatedUser.getName()) != null){
        messageToUser.setText("User with this name already exists");
        AccountingSystemController.addUser(accountingSystem, activeUser);
      } else {
        activeUser = updatedUser;
        AccountingSystemController.addUser(accountingSystem, activeUser);
        messageToUser.setText("User updated");
      }
    }
  }

  private User getUpdateUser() {
    if (typeNotSelected()) {
      messageToUser.setText("User must have a type");
      return null;
    }

    if (hasSpaces()) {
      messageToUser.setText("Username and password \n cannot have spaces");
      return null;
    }
    if (emptyField()) {
      messageToUser.setText("Missing required information");
      return null;
    }

    UserType type = UserType.COMPANY;
    if (radioBtnPrivate.isSelected()) {
      type = UserType.PRIVATE;
    }
    User user = null;
    try {
      user =
          new User(
              type,
              usernameField.getText(),
              userPasswordField.getText(),
              userContactField.getText());
    } catch (NullPointerException exception) {
      messageToUser.setText("Check contact info before update");
    }
    return user;
  }

  public void deleteUserBtnClick(ActionEvent actionEvent) {}

  private boolean typeNotSelected() {
    return !radioBtnPrivate.isSelected() && !radioBtnCompany.isSelected();
  }

  private boolean emptyField() {
    return usernameField.getText().replaceAll("\\s", "").isEmpty()
        || userPasswordField.getText().replaceAll("\\s", "").isEmpty();
  }

  private boolean hasSpaces() {
    if (usernameField.getText().contains(" ") || userPasswordField.getText().contains(" "))
      return true;
    return false;
  }

  private void loadUserList() {
    messageToUser.setText("");
    Stage popUpWindow = new Stage();

    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle("User list");

    ListView users = new ListView();
    for (User user : accountingSystem.getUsers()) {
      users.getItems().add(user.toString());
    }

    Button backBtn = new Button("Back");
    backBtn.setOnAction(e -> popUpWindow.close());
    VBox layout = new VBox(10);

    layout.getChildren().addAll(backBtn, users);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 500, 300);

    popUpWindow.setScene(scene1);

    popUpWindow.showAndWait();
  }
}
