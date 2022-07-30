package financeapp.frontend.base;

import financeapp.users.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    private final UserRepo userRepo;

    public BaseController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/accounts")
    public String accounts(Model model, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var accounts = user.getAccountList();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

}