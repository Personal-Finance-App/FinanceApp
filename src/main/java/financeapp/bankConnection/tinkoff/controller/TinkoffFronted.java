package financeapp.bankConnection.tinkoff.controller;

import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TinkoffFronted {

    private final UserRepo userRepo;
    private final TinkoffConnectionRepo tinkoffConnectionRepo;

    @GetMapping("/tinkof/login")
    public String accounts(Model model, Authentication authentication) {
        return "/banksRegister/tinkof/login_template";
    }

    @GetMapping("/tinkof/requestAccount")
    public String requestAccount(Model model, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null)
            return accounts(model, authentication);
        return "/banksRegister/tinkof/add_account_template";
    }
}
