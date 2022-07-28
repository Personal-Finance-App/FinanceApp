package financeapp.bankConnection.tinkoff.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TinkoffFronted {

    @GetMapping("/tinkof/login")
    public String accounts(Model model, Authentication authentication) {
        return "/banksRegister/tinkof/login_template";
    }
}
