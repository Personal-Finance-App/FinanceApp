package financeapp.bankConnection.sberbank.controllers;

import financeapp.bankConnection.sberbank.SberbankConnectionRepo;
import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SberbankFronted {

    private final UserRepo userRepo;
    private final SberbankConnectionRepo sberbankConnectionRepo;

    @GetMapping("/sberbank/login")
    public String accounts(Model model, Authentication authentication) {
        return "/banksRegister/sberbank/sberbankLogin.html";
    }

    @GetMapping("/sberbank/requestAccount")
    public String requestAccount(Model model, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var connection = sberbankConnectionRepo.getSberbankConnectionByUser(user);
        if (connection == null)
            return accounts(model, authentication);
        return "/banksRegister/sberbank/sberbankAddAccount.html";
    }
}
