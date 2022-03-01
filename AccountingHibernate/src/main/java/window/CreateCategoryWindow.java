package window;

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
import model.AccountingSystem;
import model.Category;
import model.User;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import service.CategoryService;

import javax.persistence.EntityManagerFactory;
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
    private EntityManagerFactory entityManagerFactory;
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
    public void initialize(URL location, ResourceBundle resources) {
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        if (accountingSystem != null)
            accountingSystem = accountingSystemHib.getById(accountingSystem.getId());
    }

    public void createBtnClick(ActionEvent actionEvent) throws IOException {
        Category category = createCategory();
        if (category != null) {
            if(validCategory(category)){
                if (parentCategory == null) {
                    activeUser.getCategories().add(category);
                    Popup.display(
                            "Category creation",
                            CategoryService.create(entityManagerFactory, accountingSystem, category),
                            "Okay");
                    activeUser.getCategories().remove(category);
                    loadAccountingWindow();
                } else {
                    activeUser.getCategories().add(category);
                    Popup.display(
                            "Subcategory creation",
                            CategoryService.createSubCategory(entityManagerFactory, accountingSystem, category),
                            "Okay");
                    activeUser.getCategories().remove(category);
                    loadManageCategoryWindow();
                }
            } else {
                Popup.display(
                        "Subcategory creation",
                        "Category with this name already exists",
                        "Okay");
            }
        }
    }

    private boolean validCategory(Category category) {
        for(Category categoryInSys: accountingSystem.getCategories()){
            if(categoryInSys.getTitle().equals(category.getTitle())) return false;
        }
        return true;
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
        if (parentCategory == null)
            loadAccountingWindow();
        else
            loadManageCategoryWindow();
    }

    private void loadAccountingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AccountingWindow.fxml"));
        Parent root = loader.load();
        AccountingWindow accounting = loader.getController();
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        accounting.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        accounting.setEntityManagerFactory(entityManagerFactory);
        accounting.setUser(activeUser);
        accounting.setCategoryList(accountingSystem);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + activeUser.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private void loadManageCategoryWindow() throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ManageCategoryWindow.fxml"));
        Parent root = loader.load();
        ManageCategoryWindow manageCategoryWindow = loader.getController();

        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        manageCategoryWindow.setEntityManagerFactory(entityManagerFactory);
        manageCategoryWindow.setDisplayInformation(accountingSystemHib.getById(accountingSystem.getId()), categoryHibController.getById(parentCategory.getId()), activeUser);

        Stage stage = (Stage) createBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + activeUser.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private boolean emptyField() {
        return titleField.getText().replaceAll("\\s", "").isEmpty()
                || descriptionField.getText().replaceAll("\\s", "").isEmpty();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
