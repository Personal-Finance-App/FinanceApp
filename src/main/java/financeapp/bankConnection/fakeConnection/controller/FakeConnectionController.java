package financeapp.bankConnection.fakeConnection.controller;

import financeapp.accounts.models.Account;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class FakeConnectionController {
    private final FakeConnectionService fakeConnectionService;

    public FakeConnectionController(FakeConnectionService fakeConnectionService) {
        this.fakeConnectionService = fakeConnectionService;
    }
    @PostMapping("/fake/addTransaction")
    public ResponseEntity<Collection<AbstractTransaction>> addTransaction(@RequestParam (value = "IdAccount") String id) {
        return ResponseEntity.ok().body(fakeConnectionService.addTransactions(id));
    }

    @PostMapping("/fake/createAccount")
    public ResponseEntity<Account> createAccount(@RequestParam (value = "EmailUser") String email,
                                                 @RequestParam (value = "Type") String type){
        return ResponseEntity.ok().body(fakeConnectionService.CreateAccount(type, email));
    }

    @PostMapping("/fake/createAccounts")
    public ResponseEntity<Collection<Account>> createAccounts(@RequestParam (value = "EmailUser") String email){
        return  ResponseEntity.ok().body(fakeConnectionService.CreateAccounts(email));
    }
}
