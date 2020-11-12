package window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccountingSystem;
import persistenceController.AccountingSystemHib;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
    public Button loadBtn;
    public Label systemInformation;
    private AccountingSystem accountingSystem;
    private EntityManagerFactory entityManagerFactory;
    private AccountingSystemHib accountingSystemHib;
    public Button loginBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    private void loadLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginWindow.fxml"));
        Parent root = loader.load();
        //            ObjectIO.readObjectFromFile(accountingSystem);
        LoginWindow login = loader.getController();

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void loginBtnClick(ActionEvent actionEvent) throws IOException {
        loadLoginWindow();
    }

    public void createAccountingSystem(ActionEvent actionEvent) {
    }

    public void startAccountingSystem(ActionEvent actionEvent) {
    }

    public void createDemoBtnClick(ActionEvent actionEvent) {
    }

    public void loadBtnClick(ActionEvent actionEvent) throws IOException {
        loadDatabaseWindow();
    }

    private void loadDatabaseWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/DatabasesWindow.fxml"));
        Parent root = loader.load();
        DatabasesWindow databasesWindow = loader.getController();
        if(accountingSystem != null)
            databasesWindow.setAccountingSystem(accountingSystem);
        databasesWindow.setAccountingSystemHib(accountingSystemHib);

        Stage stage = (Stage) loadBtn.getScene().getWindow();
        stage.setTitle("Accounting System databases ");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
        setSystemInformation();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public AccountingSystemHib getAccountingSystemHib() {
        return accountingSystemHib;
    }

    public void setAccountingSystemHib(AccountingSystemHib accountingSystemHib) {
        this.accountingSystemHib = accountingSystemHib;
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
}
