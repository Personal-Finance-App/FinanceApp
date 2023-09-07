package financeapp.frontend.base;

import financeapp.category.CategoriesList;
import financeapp.category.CategoryService;
import financeapp.category.InitializeCategory;
import financeapp.monthReport.TimeSpanData;
import financeapp.monthReport.services.ReportService;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.UserService;
import lombok.AllArgsConstructor;
import org.mockito.Mockito;
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
    private final ReportService reportService;

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

    @GetMapping("/category-history/{id}/{lenght}")
    public String categoryHistory(Model model,
                                  @PathVariable(value = "id") Long id,
                                  @PathVariable(value = "lenght") Integer len,
                                  Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        model.addAttribute("init", reportService.getCategoriesHistory(user, id, len));
        var categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("length", len);
        return "categoryHistory.html";
    }


    @GetMapping("/category-history/{id}")
    public String categoryHistoryWithDefaultLenght(Model model,
                                  @PathVariable(value = "id") Long id,
                                  Authentication authentication) {
        return categoryHistory(model, id, 6, authentication);
    }
}
