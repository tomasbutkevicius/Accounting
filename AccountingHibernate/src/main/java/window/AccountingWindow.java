package window;

import controller.AccountingSystemController;
import controller.UserController;
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
import model.AccountingSystem;
import model.Category;
import model.User;
import model.UserType;
import persistenceController.AccountingSystemHib;
import persistenceController.CategoryHibController;
import persistenceController.UserHibController;
import service.CategoryService;
import service.UserService;

import javax.persistence.EntityManagerFactory;
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
    public RadioButton radioBtnAdmin;
    final ToggleGroup group = new ToggleGroup();
    public ListView categoryList;
    public Button addCatBtn;
    public TextField editCatNameField;
    public Label errorMessage;
    public Label messageToUserSystem;
    public Button showUsersBtn;
    private AccountingSystem accountingSystem;
    private EntityManagerFactory entityManagerFactory;
    private User activeUser;

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
        systemNameField.setText(accountingSystem.getName());
        systemDateField.setText(accountingSystem.getSystemCreationDate().toString());
        systemIncomeField.setText(Integer.toString(accountingSystem.getIncome()) + "eur");
        systemExpenseField.setText(Integer.toString(accountingSystem.getExpense()) + "eur");
    }

    public void setCategoryList(AccountingSystem accountingSystem) {
        for (Category category : CategoryService.getAllCategoriesInSystem(entityManagerFactory, accountingSystem)) {
            if(category.getParentCategory() == null){
                categoryList.getItems().add(category.getTitle());
            }
        }
    }

    public void setUser(User user) {
        this.activeUser = user;
        usernameField.setText(user.getName());
        userPasswordField.setText(user.getPassword());
        if (user.getType() == UserType.PRIVATE) {
            radioBtnAdmin.setDisable(true);
            radioBtnPrivate.setSelected(true);
        } else if (user.getType() == UserType.COMPANY){
            radioBtnAdmin.setDisable(true);
            radioBtnCompany.setSelected(true);
        } else {
            radioBtnPrivate.setDisable(true);
            radioBtnCompany.setDisable(true);
            radioBtnAdmin.setSelected(true);
            showUsersBtn.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsersBtn.setDisable(true);
        radioBtnCompany.setToggleGroup(group);
        radioBtnPrivate.setToggleGroup(group);
        radioBtnAdmin.setToggleGroup(group);
    }

    public void showUsersBtnClick(ActionEvent actionEvent) {
        errorMessage.setText("");
        loadUserList();
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        loadLoginWindow();
    }

    public void manageCatBtnClick(ActionEvent actionEvent) throws IOException {
        try{
            Category category =
                    AccountingSystemController.getCategoryByTitle(accountingSystem, categoryList.getSelectionModel().getSelectedItem().toString());
            loadManageCategoryWindow(category);
        } catch (RuntimeException e){
            errorMessage.setText("Category not selected");
        }
    }

    private void loadManageCategoryWindow(Category category) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ManageCategoryWindow.fxml"));
        Parent root = loader.load();
        ManageCategoryWindow manageCategoryWindow = loader.getController();

        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
        manageCategoryWindow.setEntityManagerFactory(entityManagerFactory);
        manageCategoryWindow.setDisplayInformation(accountingSystemHib.getById(accountingSystem.getId()), categoryHibController.getById(category.getId()), activeUser);

        Stage stage = (Stage) manageCatBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + activeUser.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void addCatBtnClick(ActionEvent actionEvent) throws IOException {
        if (activeUser.getType().equals(UserType.ADMIN))
            errorMessage.setText("Admin cannot add categories");
        else loadCreateCategoryWindow();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/CreateCategoryWindow.fxml"));
        Parent root = loader.load();
        CreateCategoryWindow createCategoryWindow = loader.getController();
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        createCategoryWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        createCategoryWindow.setEntityManagerFactory(entityManagerFactory);
        createCategoryWindow.setActiveUser(activeUser);

        Stage stage = (Stage) addCatBtn.getScene().getWindow();
        stage.setTitle("Accounting System. User " + activeUser.getName());
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private void loadLoginWindow() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginWindow.fxml"));
        Parent root = loader.load();
        LoginWindow loginWindow = loader.getController();
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
        loginWindow.setAccountingSystem(accountingSystemHib.getById(accountingSystem.getId()));
        loginWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private void updateContactInformation(String contactInformation) {
        if (!emptyField(contactInformation)) {
            activeUser.setContactInformation(contactInformation);
            UserHibController userHibController = new UserHibController(entityManagerFactory);
            userHibController.update(activeUser);
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
            if (!updatedUser.getName().equals(activeUser.getName()) && (AccountingSystemController.userNameCount(accountingSystem, updatedUser.getName()) >= 1)) {
                    messageToUser.setText("User with this name already exists");

        } else {
                activeUser.setName(updatedUser.getName());
                activeUser.setPassword(updatedUser.getPassword());
                activeUser.setContactInformation(updatedUser.getContactInformation());
                activeUser.setType(updatedUser.getType());

                AccountingSystemController.addUser(accountingSystem, activeUser);
                UserHibController userHibController = new UserHibController(entityManagerFactory);
                userHibController.update(activeUser);
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

    public void deleteUserBtnClick(ActionEvent actionEvent) {
        popUpConfirmDeleteUser(activeUser);
    }

    private void popUpConfirmDeleteUser(User user) {
        messageToUser.setText("");
        errorMessage.setText("");
        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Delete User");

        Label question =
                new Label("Are you sure you want to delete user '" + user.getName() + "'?");
        Button backBtn = new Button("Go back");
        Button deleteBtn = new Button("Delete. I am sure. Yes. Bye.");
        backBtn.setOnAction(e -> popUpWindow.close());
        deleteBtn.setOnAction(
                e -> {
                    try {
                        new UserHibController(entityManagerFactory).delete(activeUser.getId());
                        popUpWindow.close();
                        deleteUser(user);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
        VBox layout = new VBox(10);

        layout.getChildren().addAll(question, backBtn, deleteBtn);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 500, 300);

        popUpWindow.setScene(scene1);

        popUpWindow.showAndWait();
    }

    private void deleteUser(User user) throws IOException, ClassNotFoundException {
        AccountingSystemController.removeUser(accountingSystem, user);
        if(user.equals(activeUser))
            loadLoginWindow();
    }

    private boolean typeNotSelected() {
        return !radioBtnPrivate.isSelected() && !radioBtnCompany.isSelected() && !radioBtnAdmin.isSelected();
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
        for (User user : UserService.getAllUsersInSystem(entityManagerFactory, accountingSystem)) {
            users.getItems().add(user.toString());
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> popUpWindow.close());
        Button deleteBtn = new Button("Delete user");
        deleteBtn.setOnAction(e -> {
            try {
                deleteSelectedUser(users.getSelectionModel().getSelectedItem().toString());
                popUpWindow.close();
            } catch (NullPointerException ex) {
                Popup.display("error", "User not selected to delete", "ok");
            }
        });
        VBox layout = new VBox(10);

        layout.getChildren().addAll(backBtn, users, deleteBtn);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 500, 300);

        popUpWindow.setScene(scene1);

        popUpWindow.showAndWait();
    }

    private void deleteSelectedUser(String userToString) {
        User selectedUser = null;
        for (User user : accountingSystem.getUsers()) {
            if(user.toString().equals(userToString))
                selectedUser = user;
        }
        if(selectedUser == activeUser)
        {
            Popup.display("error", "You cannot delete yourself", "okay then");
        }
        else
            popUpConfirmDeleteUser(selectedUser);
    }


    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
