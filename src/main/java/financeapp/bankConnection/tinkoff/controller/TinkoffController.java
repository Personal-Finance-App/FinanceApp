package financeapp.bankConnection.tinkoff.controller;

import com.google.gson.Gson;
import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList.AccountPayload;
import financeapp.bankConnection.tinkoff.services.TinkoffService;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@RequestMapping("/tinkof")
@Hidden
public class TinkoffController {
    private final TinkoffService tinkoffService;
    private final UserRepo userRepo;
    private final AccountService accountService;
    private static final Gson gson = new Gson();
    private final TransactionService transactionService;


    /**
     * Начало процесса регистрации - отправка СМС Кода
     *
     * @param phoneData номер телефона в формате +7********** - обязательно проверять
     * @return operationTicketId - его в будущем использовать нужно будет
     */
    @PostMapping(path = "/auth/initregister", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody PhoneData phoneData, Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        String operationTicket = tinkoffService.RegisterSendSms(phoneData.getPhone(), user);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("operationTicketId", operationTicket));


    }

    /**
     * Завершение регстрации устройства
     *
     * @param data код из смс, пароль, operationTicketId
     * @return OK или не ок(
     */
    @PostMapping("/auth/finalregister")
    @ResponseBody
    public ResponseEntity<?> FinalRegister(@RequestBody FinalRegisterData data, Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        tinkoffService.RegisterFinal(data.getOperationTicket(), data.getSms(), data.getPassword(), user);
        return ResponseEntity.ok().body("OK");

    }


    /**
     * Запрашивает список аккаунтов и возвращает их пользователю
     *
     * @return список счетов пользователей
     */
    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<?> RequestAccount(Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        List<AccountPayload> accounts = tinkoffService.getAccounts(user);
        return ResponseEntity.ok().body(accounts);
    }

    @PostMapping("/accounts/confirm")
    @ResponseBody
    public ResponseEntity<?> ConfirmAccount(@RequestBody List<AccountPayload> accounts, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        List<AccountData> accountDataList = new ArrayList<>();
        accounts.forEach(accountPayload -> accountDataList.add(new AccountData(accountPayload.getName(),
                accountPayload.getId(),
                accountPayload.getExternalAccountNumber(),
                accountPayload.getAccountGroup(),
                accountPayload.getAccountBalance().getValue())));

        List<Account> result = accountDataList.stream()
                .map(accountData -> tinkoffService.createAccount(accountData, user))
                .collect(Collectors.toList());

        var saved = accountService.CreateAccountFromPayload(result, user);


        return ResponseEntity.ok().body(Collections
                .singletonMap("savedAccount", saved));
    }

    @GetMapping("/accounts/sync")
    public ResponseEntity<?> SyncAccount(Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        return SyncAccountUser(user);
    }

    public ResponseEntity<?> SyncAccountUser(CustomUser user) throws IOException {
        var accounts = user.getAccountList();
        var receivedCount = 0;
        var savedCount = 0;

        for (Account account : accounts) {
            if (account.getProvider().equals("Тинькоф")) {
                var operations = tinkoffService.getOperations(user, account);
                receivedCount += operations.size();
                var saved = transactionService.saveTransactions(operations, account, LocalDateTime.now());
                savedCount += saved;
            }
        }

        tinkoffService.updateCardsBalance(user);


        return ResponseEntity.ok().body(gson.toJson("{'message' : 'synced', " +
                "'received' : " + receivedCount + "," +
                "'saved': " + savedCount + "}"));
    }

}

@Data
class FinalRegisterData {
    private String sms;
    private String password;
    private String operationTicket;
}

@Data
class PhoneData {
    private String phone;
}