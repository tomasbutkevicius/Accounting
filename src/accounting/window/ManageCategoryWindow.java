package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.controller.CategoryController;
import accounting.controller.UserController;
import accounting.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCategoryWindow implements Initializable {
  public ListView subCategoryList;
  public Label messageToUser;
  public Button backBtn;
  public Button manageSubCatBtn;
  public Label systemNameField;
  public Label systemIncomeField;
  public Label systemExpenseField;
  public Label systemDateField;
  public Button descriptionBtn;
  public TextField titleField;
  public Button updateCategoryTitleBtn;
  public Button deleteCategoryBtn;
  public Button showUsersBtn;
  public Button addSubCatBtn;
  public TextField editSubCatNameField;
  public Label errorMessage;
  public ListView incomeList;
  public ListView expenseList;
  public Label title;
  public TextField responsibleUserNameField;
  public Label messageToAddUser;
  public Label parentTitle;
  public ListView responsibleUserList;
  private AccountingSystem accountingSystem;
  private Category category;
  private User activeUser;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void setDisplayInformation(
      AccountingSystem accountingSystem, Category category, User activeUser) {
    setAccountingSystem(accountingSystem);
    setActiveUser(activeUser);
    setCategory(category);
    setSubCategoryList(category);
    setExpenseList(category);
    setIncomeList(category);
    setResponsibleUserList(category);
  }

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
  }

  public void setCategory(Category category) {
    this.category = category;
    titleField.setText(category.getTitle());
    title.setText("'" + category.getTitle() + "'");
    if (category.getParentCategory() == null) {
      parentTitle.setText("Is a parent");
    } else {
      parentTitle.setText("'" + category.getParentCategory().getTitle() + "'");
    }
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public void setSubCategoryList(Category category) {
      subCategoryList.getItems().clear();
    for (Category subcategory : category.getSubCategories()) {
      subCategoryList.getItems().add("'" + subcategory.getTitle() + "'");
    }
  }

  public void setIncomeList(Category category) {
      incomeList.getItems().clear();
    for (Income income : category.getIncomes()) {
      incomeList.getItems().add("'" + income.getName() + "'");
    }
  }

  public void setExpenseList(Category category) {
      expenseList.getItems().clear();
    for (Expense expense : category.getExpenses()) {
      expenseList.getItems().add("'" + expense.getName() + "'");
    }
  }

  public void setResponsibleUserList(Category category) {
      responsibleUserList.getItems().clear();
    for (User user : category.getResponsibleUsers()) {
      responsibleUserList.getItems().add("'" + user.getName() + "'");
    }
  }

  public void descriptionBtnClick(ActionEvent actionEvent) {
    messageToUser.setText("");
    errorMessage.setText("");
    Stage popUpWindow = new Stage();

    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle("Category description");

    TextField categoryDescription = new TextField(category.getDescription());

    Button backBtn = new Button("Back");
    Button updateCategoryBtn = new Button("Update");
    backBtn.setOnAction(e -> popUpWindow.close());
    updateCategoryBtn.setOnAction(e -> updateDescriptionInformation(categoryDescription.getText()));
    VBox layout = new VBox(10);

    layout.getChildren().addAll(categoryDescription, backBtn, updateCategoryBtn);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 500, 300);

    popUpWindow.setScene(scene1);

    popUpWindow.showAndWait();
  }

  private void updateDescriptionInformation(String text) {
    if (!emptyField(text)) {
      category.setDescription(text);
      messageToUser.setText("Category description updated");
    }
  }

  public void backBtnClick(ActionEvent actionEvent) throws IOException {
    loadAccountingWindow();
  }

  public void manageSubCatBtnClick(ActionEvent actionEvent) {}

  public void updateCategoryTitleBtnClick(ActionEvent actionEvent) {
    if (!emptyField(titleField.getText())) {
      if (AccountingSystemController.getCategoryByTitle(accountingSystem, titleField.getText())
          != null) {
        messageToUser.setText("Category with this title already exists");
      } else {
        category.setTitle(titleField.getText());
        title.setText("'" + category.getTitle() + "'");
        messageToUser.setText("Title updated");
      }
    } else {
      messageToUser.setText("Title cannot be empty");
    }
  }

  public void deleteCategoryBtnClick(ActionEvent actionEvent) {}

  public void showUsersBtnClick(ActionEvent actionEvent) {
    loadUserList();
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

  public void addSubCatBtnClick(ActionEvent actionEvent) throws IOException {
    loadCreateCategoryWindow();
  }

  private void loadCreateCategoryWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCategoryWindow.fxml"));
    Parent root = loader.load();
    CreateCategoryWindow createCategoryWindow = loader.getController();
    createCategoryWindow.setAccountingSystem(accountingSystem);
    createCategoryWindow.setActiveUser(activeUser);
    createCategoryWindow.setParentCategory(category);

    Stage stage = (Stage) backBtn.getScene().getWindow();
    stage.setTitle("Accounting System. User " + activeUser.getName());
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  public void addIncomeBtnClick(ActionEvent actionEvent) {}

  public void addExpenseBtnClick(ActionEvent actionEvent) {}

  public void addResponsibleBtnClick(ActionEvent actionEvent) {
      if(emptyField(responsibleUserNameField.getText())){
          messageToAddUser.setText("Empty field");
      } else {
          addResponsibleUser();
      }
  }

    private void addResponsibleUser() {
        User responsibleUser = UserController.getUserByName(accountingSystem, responsibleUserNameField.getText());
        if (responsibleUser == null) {
            messageToAddUser.setText("User with this name does not exist");
        } else {
            if (CategoryController.responsibleUserExists(category.getResponsibleUsers(), responsibleUser)) {
                messageToAddUser.setText("User is already responsible");
            } else {
                CategoryController.addResponsibleUser(category, responsibleUser);
                setResponsibleUserList(category);
                messageToAddUser.setText("Added responsible user");
            }
        }
    }

    public void removeRespBtnClick(ActionEvent actionEvent) {
        if(emptyField(responsibleUserNameField.getText())){
            messageToAddUser.setText("Empty field");
        } else {
            removeResponsibleUser();
        }
    }

    private void removeResponsibleUser() {
        User responsibleUser = UserController.getUserByName(accountingSystem, responsibleUserNameField.getText());
        if (responsibleUser == null) {
            messageToAddUser.setText("User with this name does not exist");
        } else {
            if (CategoryController.responsibleUserExists(category.getResponsibleUsers(), responsibleUser)) {
                ArrayList<User> responsibleUsers = category.getResponsibleUsers();
                responsibleUsers.remove(responsibleUser);
                messageToAddUser.setText("User removed from list");
                setResponsibleUserList(category);
            } else {
                messageToAddUser.setText("User is not responsible");
            }
        }
    }

    private void loadAccountingWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountingWindow.fxml"));
    Parent root = loader.load();
    AccountingWindow accounting = loader.getController();
    accounting.setAccountingSystem(accountingSystem);
    accounting.setUser(activeUser);
    accounting.setCategoryList(accountingSystem);

    Stage stage = (Stage) backBtn.getScene().getWindow();
    stage.setTitle("Accounting System. User " + activeUser.getName());
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  private boolean emptyField(String text) {
    return text.replaceAll("\\s", "").isEmpty();
  }

}
