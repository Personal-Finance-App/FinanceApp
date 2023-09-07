package financeapp.bankConnection.sberbank.controllers;

import com.google.gson.Gson;
import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.sberbank.api.responseEntitys.AccountPayload;
import financeapp.bankConnection.sberbank.services.SberbankService;

import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/sberbank")
@Hidden
public class SberbankController {
    private final UserRepo userRepo;
    private final SberbankService sberbankService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    Gson gson = new Gson().newBuilder().create();


    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<?> RequestAccount(Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        List<AccountPayload> accounts = sberbankService.getAccounts(user);
        return ResponseEntity.ok().body(accounts);
    }

    @PostMapping("/accounts/confirm")
    @ResponseBody
    public ResponseEntity<?> ConfirmAccount(@RequestBody List<AccountPayload> accounts, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());

        var result = accounts
                .stream()
                .map(acc -> sberbankService.createAccountFromPayload(acc, user))
                .toList();

        var saved = accountService.CreateAccountFromPayload(result, user);


        return ResponseEntity.ok().body(Collections
                .singletonMap("savedAccount", saved));
    }

    public ResponseEntity<?> SyncAccountUser(CustomUser user) throws IOException {
        var accounts = user.getAccountList();
        var receivedCount = 0;
        var savedCount = 0;

        for (Account account : accounts) {
            if (account.getProvider().equals("Сбербанк")) {
                var operations = sberbankService.getOperations(user, account);
                receivedCount += operations.size();
                var saved = transactionService.saveTransactions(operations, account, LocalDateTime.now());
                savedCount += saved;
            }
        }

        sberbankService.updateCardsBalance(user);

        return ResponseEntity.ok().body(gson.toJson("{'message' : 'synced', " +
                "'received' : " + receivedCount + "," +
                "'saved': " + savedCount + "}"));
    }


    @GetMapping("/accounts/sync")
    public ResponseEntity<?> SyncAccount(Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        return SyncAccountUser(user);
    }
    


}
