package financeapp.monthReport.services;

import financeapp.monthReport.entity.Report;
import financeapp.monthReport.repos.ReportRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepo reportRepo;
    private final TransactionService transactionService;
    private final ReportPartService reportPartService;

    public ReportService(ReportRepo reportRepo, TransactionService transactionService, ReportPartService reportPartService) {
        this.reportRepo = reportRepo;
        this.transactionService = transactionService;
        this.reportPartService = reportPartService;
    }

    public Report create(CustomUser user, LocalDateTime start, LocalDateTime end) {
        var accounts = user.getAccountList();
        var syncTime = LocalDateTime.now();
        List<AbstractTransaction> transactionList = new ArrayList<>();
        accounts.forEach(account -> {
            var operations = transactionService.getTransaction(account, start, end);
            transactionList.addAll(operations);
        });

        var parts = reportPartService.sortTransactions(transactionList);

        var newReport = new Report(user, start.getYear(), start.getMonthValue(), parts, syncTime);
        reportRepo.save(newReport);
        return newReport;
    }

    ;

    public Report findReport(CustomUser user, Integer month, Integer year) throws Exception {
        var report = reportRepo.findReportByLinkedUserAndMonthAndYear(user, month, year);
        if (report == null)
            throw new Exception("Отчет еще не создан!");
        return report;
    }


}
