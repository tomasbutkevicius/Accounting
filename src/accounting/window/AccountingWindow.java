package accounting.window;

import accounting.model.AccountingSystem;
import accounting.model.User;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountingWindow implements Initializable {
    AccountingSystem accountingSystem;
    User activeUser;

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public void setUser(User user) {
        this.activeUser = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
