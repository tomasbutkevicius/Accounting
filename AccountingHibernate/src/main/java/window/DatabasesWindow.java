package window;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.AccountingSystem;
import persistenceController.AccountingSystemHib;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabasesWindow implements Initializable {
    public ListView databaseList;
    public Button selectDbBtn;
    public Button backBtn;
    public Label errorMessage;
    private EntityManagerFactory entityManagerFactory;
    private AccountingSystem accountingSystem;
    private AccountingSystemHib accountingSystemHib;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    private void setDatabaseList() {
        databaseList.getItems().clear();
        for (AccountingSystem accountingSystem : accountingSystemHib.getAccountingSystemList()) {
            databaseList
                    .getItems()
                    .add("ID: " + accountingSystem.getId() + " Name: " + accountingSystem.getName());
        }
    }

    public AccountingSystemHib getAccountingSystemHib() {
        return accountingSystemHib;
    }

    public void setAccountingSystemHib(AccountingSystemHib accountingSystemHib) {
        this.accountingSystemHib = accountingSystemHib;
        setDatabaseList();
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        loadStartWindow();
    }

    private void loadStartWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StartWindow.fxml"));
        Parent root = loader.load();
        StartWindow startWindow = loader.getController();

        if (accountingSystem != null)
            startWindow.setAccountingSystem(accountingSystem);

        startWindow.setAccountingSystemHib(accountingSystemHib);
        startWindow.setEntityManagerFactory(entityManagerFactory);

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setTitle("Accounting System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void selectDbBtnClick(ActionEvent actionEvent) {
        try {
            String selectedDatabase = databaseList.getSelectionModel().getSelectedItem().toString();
            String[] splitSelectedDb = selectedDatabase.split(" ");
            int id = Integer.parseInt(splitSelectedDb[1]);
            accountingSystem = accountingSystemHib.getById(id);
            loadStartWindow();
        } catch (RuntimeException e) {
            errorMessage.setText("Database not selected");
        } catch (IOException e){
            errorMessage.setText("Something went wrong");
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    //    public void manageSubCatBtnClick(ActionEvent actionEvent) throws IOException {
//        try {
//            Category subcategory =
//                    CategoryController.getSubcategoryByName(
//                            category, subCategoryList.getSelectionModel().getSelectedItem().toString());
//            loadSubCategoryWindow(subcategory);
//        } catch (RuntimeException e) {
//            errorMessage.setText("Category not selected");
//        }
//    }
}
