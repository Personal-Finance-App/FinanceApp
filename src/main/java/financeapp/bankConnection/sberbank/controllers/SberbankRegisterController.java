package financeapp.bankConnection.sberbank.controllers;

import financeapp.bankConnection.sberbank.services.SberbankRegisterService;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/sberbank/auth")
@Hidden
public class SberbankRegisterController {
    public SberbankRegisterController(SberbankRegisterService sberbankRegisterService, UserRepo userRepo) {
        this.sberbankRegisterService = sberbankRegisterService;
        this.userRepo = userRepo;
    }

    private final SberbankRegisterService sberbankRegisterService;
    private final UserRepo userRepo;


    @PostMapping(path = "/initregister", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody LoginData data, Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        String mGUID = sberbankRegisterService.requestSms(data.getLogin());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("mGUID", mGUID));
    }

    @PostMapping("/finalregister")
    @ResponseBody
    public ResponseEntity<?> FinalRegister(@RequestBody FinalRegisterData data, Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        if (data.getMguid().isEmpty())
            throw new RuntimeException("MGUID Can't be empty");

        if (data.getPinCode().isEmpty())
            throw new RuntimeException("Pin code can't be empty");

        if (data.getSmsCode().isEmpty())
            throw new RuntimeException("Sms code can't be empty");

        if (sberbankRegisterService.registerFinal(data.getMguid(), user, data.getSmsCode(), data.getPinCode()))
            return ResponseEntity.ok().body("OK");
        throw new RuntimeException("Something happend with Sberbank API");
    }


}


@Data
@NoArgsConstructor
class LoginData {
    private String login;
}

@NoArgsConstructor
@Data
class FinalRegisterData {
    private String mguid = "";
    private String smsCode = "";
    private String pinCode = "";
}