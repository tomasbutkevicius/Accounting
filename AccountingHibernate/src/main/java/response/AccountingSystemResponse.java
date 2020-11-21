package response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.AccountingSystem;
import model.Category;
import model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountingSystemResponse {
    private int id;
    private String name;
    private LocalDate systemCreationDate;
    private String systemVersion;
    private List<User> users = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private int income;
    private int expense;


    public AccountingSystemResponse(AccountingSystem accountingSystem){
        this.id = accountingSystem.getId();
        this.name = accountingSystem.getName();
        this.systemCreationDate = accountingSystem.getSystemCreationDate();
        this.systemVersion = accountingSystem.getSystemVersion();
        this.users = accountingSystem.getUsers();
        this.categories = accountingSystem.getCategories();
        this.income = accountingSystem.getIncome();
        this.expense = accountingSystem.getExpense();
    }
}
