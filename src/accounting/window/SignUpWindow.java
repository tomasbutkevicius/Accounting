package accounting.window;

import accounting.model.AccountingSystem;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpWindow implements Initializable {
    private AccountingSystem accountingSystem;

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
