package financeapp.bankConnection.tinkoff.controller;

import com.google.gson.Gson;
import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList.AccountPayload;
import financeapp.bankConnection.tinkoff.services.TinkoffService;
import financeapp.transaction.services.TransactionService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@RequestMapping("/tinkof")
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
    public ResponseEntity<?> Register(@RequestBody PhoneData phoneData, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        Logger logger = LoggerFactory.getLogger(TinkoffController.class);
        String operationTicket;
        try {
            operationTicket = tinkoffService.RegisterSendSms(phoneData.getPhone(), user);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.internalServerError().body(gson.toJson("{'error' : '" + e.getLocalizedMessage() + "'}"));
        }
        return ResponseEntity.ok().body(gson.toJson("{\"operationTicketId\" : \"" + operationTicket + "\"}"));
    }

    /**
     * Завершение регстрации устройства
     *
     * @param data код из смс, пароль, operationTicketId
     * @return OK или не ок(
     */
    @PostMapping("/auth/finalregister")
    public ResponseEntity<?> FinalRegister(@RequestBody FinalRegisterData data, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        try {
            tinkoffService.RegisterFinal(data.getOperationTicket(), data.getSms(), data.getPassword(), user);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(gson.toJson("{'error' : '" + e.getLocalizedMessage() + "'}"));
        }
        return ResponseEntity.ok().body(data.getOperationTicket() + " " + data.getPassword() + " " + data.getSms());
    }


    /**
     * Запрашивает список аккаунтов и возвращает их пользователю
     *
     * @return список счетов пользователей
     */
    @GetMapping("/accounts")
    public ResponseEntity<?> RequestAccount(Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        List<AccountPayload> accounts = new ArrayList<>();
        try {
            accounts = tinkoffService.getAccounts(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(gson.toJson(accounts));
    }

    @PostMapping("/accounts/confirm")
    public ResponseEntity<?> ConfirmAccount(@RequestBody List<AccountPayload> accounts, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        List<AccountData> accountDataList = new ArrayList<>();
        accounts.forEach(accountPayload -> accountDataList.add(new AccountData(accountPayload.getName(),
                accountPayload.getId(),
                accountPayload.getExternalAccountNumber(),
                accountPayload.getAccountGroup())));

        List<Account> result = accountDataList.stream()
                .map(accountData -> tinkoffService.createAccount(accountData, user))
                .collect(Collectors.toList());

        var saved = accountService.CreateAccountFromPayload(result);


        return ResponseEntity.ok().body(gson.toJson("{'message' : 'created', 'savedAccount' : " + saved + "}"));
    }

    @GetMapping("/accounts/sync")
    public ResponseEntity<?> SyncAccount(Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var accounts = user.getAccountList();
        var receivedCount = 0;
        var savedCount = 0;

        accounts.forEach(account -> {
            if (account.getProvider().equals("Тинькоф")) {
                try {
                    var operations = tinkoffService.getOperations(user, account);
                    var saved = transactionService.saveTransactions(operations, account, LocalDateTime.now());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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