package accounting.window;

import accounting.controller.AccountingSystemController;
import accounting.model.AccountingSystem;
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
    AccountingSystem accountingSystem;
    User activeUser;

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
        systemNameField.setText(accountingSystem.getName());
        systemDateField.setText(accountingSystem.getSystemCreationDate().toString());
        systemIncomeField.setText(Integer.toString(accountingSystem.getIncome()));
        systemExpenseField.setText(Integer.toString(accountingSystem.getExpense()));
    }

    public void setUser(User user) {
        this.activeUser = user;
        usernameField.setText(user.getName());
        userPasswordField.setText(user.getPassword());
        if(user.getType() == UserType.PRIVATE){
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

    public void backBtnClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        loadLoginWindow();
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

    public void showUserContactInfo(ActionEvent actionEvent) {
            messageToUser.setText("");
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

    private void updateContactInformation(String contactInformation) {
        if(!emptyField(contactInformation)){
            activeUser.setContactInformation(contactInformation);
            messageToUser.setText("User contact info updated");
        }
    }

    private boolean emptyField(String text) {
        return text.replaceAll("\\s", "").isEmpty();
    }

    public void updateUser(ActionEvent actionEvent) {
        messageToUser.setText("");
        User user = getUpdateUser();
        if(user!=null){
            AccountingSystemController.removeUser(accountingSystem, activeUser);
            activeUser = user;
            AccountingSystemController.addUser(accountingSystem, activeUser);
            messageToUser.setText("User updated");
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
        try{
            user = new User(type, usernameField.getText(), userPasswordField.getText(), userContactField.getText());
        } catch (NullPointerException exception){
            messageToUser.setText("Check contact info before update");
        }
        return user;
    }

    public void deleteUser(ActionEvent actionEvent) {
    }

    private boolean typeNotSelected() {
        return !radioBtnPrivate.isSelected() && !radioBtnCompany.isSelected();
    }

    private boolean emptyField() {
        return usernameField.getText().replaceAll("\\s", "").isEmpty()
                || userPasswordField.getText().replaceAll("\\s", "").isEmpty();
    }

    private boolean hasSpaces() {
        if (usernameField.getText().contains(" ") || userPasswordField.getText().contains(" ")) return true;
        return false;
    }

    public void showUsersBtnClick(ActionEvent actionEvent) {
        loadUserList();
    }

    private void loadUserList() {
        messageToUser.setText("");
        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("User list");

        ListView users = new ListView();
        for(User user: accountingSystem.getUsers()){
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
