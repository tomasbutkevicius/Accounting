package window;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
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

    public void loadBtnClick(ActionEvent actionEvent) {
    }
}
