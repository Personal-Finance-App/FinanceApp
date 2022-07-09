package financeapp.bankConnection.tinkoff.controller;

import financeapp.bankConnection.tinkoff.services.TinkoffService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class TinkoffController {
    private final TinkoffService tinkoffService;
    private final UserRepo userRepo;


    @PostMapping("/auth/initregister")
    public ResponseEntity<?> Register(@RequestParam String phone, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        String operationTicket = null;
        try {
            operationTicket = tinkoffService.RegisterSendSms(phone, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(operationTicket);
    }

    // Доставать Юзера
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

}

@Data
class FinalRegisterData {
    private String sms;
    private String password;
    private String operationTicket;
}