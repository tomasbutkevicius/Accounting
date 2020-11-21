package webService;

import model.AccountingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistenceController.AccountingSystemHib;
import response.AccountingSystemResponse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingSystemService {
    private AccountingSystemHib accountingSystemHib ;

    @Autowired
    public AccountingSystemService(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accounting_hib");
        this.accountingSystemHib = new AccountingSystemHib(entityManagerFactory);
    }

    public List<AccountingSystemResponse> findAll() {
        List<AccountingSystem> accountingSystems = accountingSystemHib.getAccountingSystemList();
        List<AccountingSystemResponse> responseList = new ArrayList<>();

        for (AccountingSystem accountingSystem : accountingSystems) {
            responseList.add(new AccountingSystemResponse(accountingSystem));
        }
        return responseList;
    }
}
