package financeapp.frontend.base;

import financeapp.accounts.models.variousAccount.SavingAccount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PlanControllerFront {
    @GetMapping("/new-plan")
    public String newPlan(Model model) {
        model.addAttribute("planId", -1);
        return "createPlan";
    }

    @GetMapping("/edit-plan/{planId}")
    public String editPlan(Model model, @PathVariable("planId") Long planId) {
        model.addAttribute("planId", planId);
        return "createPlan";
    }
}
