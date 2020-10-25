package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.controller.CategoryController;
import accounting.model.AccountingSystem;
import accounting.model.Category;
import accounting.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateCategoryWindow implements Initializable {
  public Label errorMessage;
  public Button backBtn;
  public Label responsibleUserField;
  public TextField titleField;
  public TextArea descriptionField;
  public Button createBtn;
  private AccountingSystem accountingSystem;
  private User activeUser;
  private Category parentCategory;

  public void setAccountingSystem(AccountingSystem accountingSystem) {
    this.accountingSystem = accountingSystem;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
    responsibleUserField.setText(activeUser.getName());
  }

  public void setParentCategory(Category category) {
    this.parentCategory = category;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void createBtnClick(ActionEvent actionEvent) {
    Category category = createCategory();
    if (category != null) {
        if(parentCategory == null)
        {
            Popup.display(
                    "Category creation",
                    AccountingSystemController.addCategory(accountingSystem, category),
                    "Okay");
        } else {
            parentCategory.getSubCategories().add(category);
            Popup.display(
                    "Subcategory creation",
                    "subcategory Added",
                    "Okay");
        }
    }
  }

  private Category createCategory() {
    if (emptyField()) {
      errorMessage.setText("Missing required information");
    } else {
      if (parentCategory == null) {
        ArrayList<User> responsibleUsers = new ArrayList<>();
        responsibleUsers.add(activeUser);
        return new Category(titleField.getText(), descriptionField.getText(), responsibleUsers);
      } else {
          ArrayList<User> responsibleUsers = new ArrayList<>();
          responsibleUsers.add(activeUser);
          return new Category(titleField.getText(), descriptionField.getText(), responsibleUsers, parentCategory);
      }
    }
    return null;
  }

  public void backBtnClick(ActionEvent actionEvent) throws IOException {
      if(parentCategory == null)
            loadAccountingWindow();
      else
          loadManageCategoryWindow();
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

    private void loadManageCategoryWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCategoryWindow.fxml"));
        Parent root = loader.load();
        ManageCategoryWindow manageCategoryWindow = loader.getController();
        manageCategoryWindow.setDisplayInformation(accountingSystem, parentCategory, activeUser);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + activeUser.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

  private boolean emptyField() {
    return titleField.getText().replaceAll("\\s", "").isEmpty()
        || descriptionField.getText().replaceAll("\\s", "").isEmpty();
  }
}