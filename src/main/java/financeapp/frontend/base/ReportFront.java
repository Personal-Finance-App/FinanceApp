package financeapp.frontend.base;

import financeapp.monthReport.TimeSpanData;
import financeapp.monthReport.entity.Label;
import financeapp.monthReport.entity.ReportCategoryPart;
import financeapp.monthReport.repos.LabelRepo;
import financeapp.monthReport.repos.ReportRepo;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ReportFront {
    private final UserRepo userRepo;
    private final ReportRepo reportRepo;
    private final TransactionRepo transactionRepo;
    private final LabelRepo labelRepo;

    @GetMapping("/report/{year}/{month}")
    public String reportSee(Model model,
                            @PathVariable(value = "year") Integer year,
                            @PathVariable(value = "month") Integer month,
                            Authentication authentication) {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var report = reportRepo.findReportByLinkedUserAndMonthAndYear(user, month, year);

        model.addAttribute("month", monthNames[month - 1]);
        model.addAttribute("monthNum", month);
        model.addAttribute("year", year);
        if (report == null)
            throw new RuntimeException("Отчет еще не создан");

        var parts = report.getParts();
        parts.sort(Comparator.comparingDouble(ReportCategoryPart::getSum).reversed());
        var sum = parts.stream().map(ReportCategoryPart::getSum).toList();
        var ids = parts.stream().map(i -> i.getCategory().getId()).toList();
        var labels = parts.stream().map(item -> item.getCategory().getCategoryName()).toList();
        var totalSpend = sum.stream().mapToDouble(f -> f).sum();
        model.addAttribute("categoryNames", labels);
        model.addAttribute("categoryAmounts", sum);
        model.addAttribute("categoryIds", ids);
        model.addAttribute("totalSpend", totalSpend);
        model.addAttribute("categorySpendPercentage",
                sum.stream().map(item -> item / totalSpend * 100).collect(Collectors.toList()));

        model.addAttribute("categoryParts", parts);
        model.addAttribute("analysis", report.getAnalysis());
        model.addAttribute("comment", report.getComment());


        return "report.html";
    }

    @GetMapping("/reports")
    public String allReports(Model model, Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var reports = reportRepo.findReportByLinkedUser(user);
        model.addAttribute("reports", reports);

        return "reports.html";
    }

    @GetMapping("/monthTransaction/{year}/{month}")
    public String transactionList(Model model, Authentication authentication,
                                  @PathVariable Integer year, @PathVariable Integer month) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var time = new TimeSpanData(month, year);
        var transactions = new LinkedList<AbstractTransaction>();
        user.getAccountList().forEach(account -> {
            transactions.addAll(
                    transactionRepo.
                            getAllByDateTimeAfterAndDateTimeBeforeAndAccount(
                                    time.getTimeStart(), time.getTimeEnd(), account));
        });

        List<Label> labelList = labelRepo.findAll();

        model.addAttribute("transactions", transactions);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("labels", labelList);

        return "transationListPerMonth.html";
    }


}

