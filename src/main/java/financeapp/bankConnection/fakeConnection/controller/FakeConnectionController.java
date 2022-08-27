package financeapp.bankConnection.fakeConnection.controller;

import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class FakeConnectionController {
    private final FakeConnectionService fakeConnectionService;
    private final AccountService accountService;

    public FakeConnectionController(FakeConnectionService fakeConnectionService, AccountService accountService) {
        this.fakeConnectionService = fakeConnectionService;
        this.accountService = accountService;
    }
    @PostMapping("/fake/addTransaction")
    public ResponseEntity<Collection<AbstractTransaction>> addTransaction(@RequestParam (value = "IdAccount") String id) {
        return ResponseEntity.ok().body(fakeConnectionService.AddTransactions(id));
    }

    @PostMapping("/fake/createAccount")
    public ResponseEntity<Account> createAccount(Authentication authentication,
                                                 @RequestParam (value = "Type") String type){
        return ResponseEntity.ok().body(fakeConnectionService.CreateAccount(type, authentication.getName()));
    }

    @PostMapping("/fake/createAccounts")
    public ResponseEntity<Collection<Account>> createAccounts(Authentication authentication){
        return  ResponseEntity.ok().body(fakeConnectionService.CreateAccounts(authentication.getName()));
    }

    @GetMapping("/fake/getAll")
    public ResponseEntity<Collection<Account>> getAll(){
        return ResponseEntity.ok().body(accountService.getAll());
    }
}
