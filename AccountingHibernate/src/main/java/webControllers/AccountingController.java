package webControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.AccountingSystemResponse;
import webService.AccountingSystemService;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class AccountingController {
    private final AccountingSystemService accountingSystemService;

    @Autowired
    public AccountingController(AccountingSystemService accountingSystemService){
        this.accountingSystemService = accountingSystemService;
    }

    @GetMapping
    public List<AccountingSystemResponse> getAllAccountingSystems(){
        return accountingSystemService.findAll();
    }
}
