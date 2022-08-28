package financeapp.frontend.base;

import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.services.AccountService;
import financeapp.financeGoal.FinanceGoalService;
import financeapp.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class GoalsController {
    private final FinanceGoalService service;
    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/goals")
    public String accounts(Model model, Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        var goals = service.getAllByUser(user);
        var usedAcc = goals.stream().map(goal -> goal.getLinkedAccount()).toList();
        model.addAttribute("goals", goals);
        var accounts = accountService.getAccountByUser(user)
                .stream()
                .filter(item -> item instanceof SavingAccount)
                .filter(item -> !usedAcc.contains(item))
                .toList();
        model.addAttribute("accounts", accounts);

        var saved = goals.stream().map(i -> i.getLinkedAccount().getBalance()).mapToDouble(f -> f).sum();
        var totalMonthlyFee = goals.stream().map(i -> i.MonthlyFee()).mapToDouble(f -> f).sum();
        model.addAttribute("saved", saved);
        model.addAttribute("totalMonthlyFee", totalMonthlyFee);

        return "goals";
    }
}
