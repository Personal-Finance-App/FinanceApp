package financeapp.frontend.base;

import financeapp.accounts.models.variousAccount.SavingAccount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlanControllerFront {
    @GetMapping("/new-plan")
    public String accounts(Model model, Authentication authentication) {

        return "createPlan";
    }
}
