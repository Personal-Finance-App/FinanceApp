package financeapp;

import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.services.AccountService;
import financeapp.monthReport.TimeSpanData;
import financeapp.monthReport.entity.Report;
import financeapp.monthReport.repos.ReportRepo;
import financeapp.monthReport.services.AnalysisService;
import financeapp.monthReport.services.ReportPartService;
import financeapp.monthReport.services.ReportService;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class ReportServiceTest extends AbstractTest{
    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepo reportRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private ReportPartService reportPartService;


    @Test
    public void updateReportTest() throws Exception {
        var user = new CustomUser("we@new.com", "qwerty");
        var account = new DebitAccount("1", "debit", user, "testReport");
        TimeSpanData time = new TimeSpanData(1, 2023);
        account.AddTransaction(new PayTransaction(1000, "money", LocalDateTime.of(2023, 1,2, 13, 20), "shop"));
        user.addAccount(account);

        var accounts = user.getAccountList();
        var syncTime = LocalDateTime.now();
        List<AbstractTransaction> transactionList = new ArrayList<>();
        accounts.forEach(acc -> {
            var operations = transactionService.getTransaction(account, time.getTimeStart(), time.getTimeEnd());
            transactionList.addAll(operations);
        });
        var analysis = analysisService.startAnalysis(transactionList);
        var parts = reportPartService.sortTransactions(transactionList);

        var report = new Report(user, 2023, 1, parts, syncTime);

        report.setAnalysis(analysis);
        reportRepo.save(report);
        var report_time = report.getUpdated();
        var account2 = new DebitAccount("2", "debit", user, "testReport2");
        account2.AddTransaction(new PayTransaction(1000000, "money", LocalDateTime.now(), "dress"));
        user.addAccount(account2);
        var rep = reportService.updateReport(report, user);
        var check = false;
        if (rep.getUpdated().isAfter(report_time)){
            check = true;
        }
        Assert.assertEquals(check, true);
    }

}
