package financeapp.frontend.base;

import financeapp.monthPlan.PlanService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BaseController {
    private final UserRepo userRepo;
    private final PlanService planService;


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

    @GetMapping("/plans")
    public String plans(Model model, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var plans = planService.monthPlansByUser(user);
        model.addAttribute("plansList", plans);
        return "plans";
    }
}