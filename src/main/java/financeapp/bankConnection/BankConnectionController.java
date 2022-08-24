package financeapp.bankConnection;

import financeapp.bankConnection.Interface.BankConnection;
import financeapp.bankConnection.Interface.BankConnectionRepo;
import financeapp.bankConnection.sberbank.SberbankConnection;
import financeapp.bankConnection.sberbank.controllers.SberbankController;
import financeapp.bankConnection.tinkoff.TinkoffConnection;
import financeapp.bankConnection.tinkoff.controller.TinkoffController;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/acc")
public class BankConnectionController {
    private final UserRepo userRepo;
    private final BankConnectionRepo bankConnectionRepo;
    private final TinkoffController tinkoffController;
    private final SberbankController sberbankController;


    @GetMapping(path = "/sync-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> SyncAll(Authentication authentication) throws IOException {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var connections = bankConnectionRepo.getBankConnectionByUser(user);
        for (BankConnection conn : connections) {
            if (conn instanceof TinkoffConnection)
                tinkoffController.SyncAccountUser(user);
            if (conn instanceof SberbankConnection)
                sberbankController.SyncAccountUser(user);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("OK");


    }
}
