package financeapp.frontend.base;

import financeapp.category.CategoryService;
import financeapp.monthReport.TimeSpanData;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class CategoryControllerFront {

    private final UserService userService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    @GetMapping("/need-specification/{year}/{month}")
    public String needSpecification(Model model,
                                    @PathVariable(value = "year") Integer year,
                                    @PathVariable(value = "month") Integer month, Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        var data = new TimeSpanData(month, year);
        var transaction = new ArrayList<AbstractTransaction>();
        var categories = categoryService.getAll();

        user.getAccountList().forEach(account -> {
            transaction.addAll(
                    transactionService.getTransactionWithoutCategory(account, data.getTimeStart(), data.getTimeEnd())
            );
        });

        model.addAttribute("toSpecification", transaction);
        model.addAttribute("categories", categories);
        return "categorySpecification";

    }
}
