package financeapp.bankConnection.tinkoff.controller;

import com.google.gson.Gson;
import financeapp.bankConnection.tinkoff.services.TinkoffService;
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
import java.util.Arrays;


@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class TinkoffController {
    private final TinkoffService tinkoffService;
    private final UserRepo userRepo;
    private static final Gson gson = new Gson();


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
            return ResponseEntity.ok().body(gson.toJson("{'error' : '" + e.getLocalizedMessage() + "'}"));
        }
        return ResponseEntity.ok().body(gson.toJson("{'operationTicketId' : '" + operationTicket + "'}"));
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
        }
        return ResponseEntity.ok().body("OK");
    }


    /**
     * Запрашивает список аккаунтов и возращает их пользователю
     *
     * @return
     */
    @GetMapping("/accounts")
    public ResponseEntity<?> RequestAccount(Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        try {
            var accounts = tinkoffService.getAccounts(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("OK");
    }

    ;
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