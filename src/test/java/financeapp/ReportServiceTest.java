package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.monthReport.TimeSpanData;
import financeapp.monthReport.entity.Report;
import financeapp.monthReport.entity.ReportCategoryPart;
import financeapp.monthReport.repos.AnalysisRepo;
import financeapp.monthReport.repos.LabelRepo;
import financeapp.monthReport.repos.ReportRepo;
import financeapp.monthReport.services.AnalysisService;
import financeapp.monthReport.services.LabelService;
import financeapp.monthReport.services.ReportPartService;
import financeapp.monthReport.services.ReportService;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceTest extends AbstractTest {
    @TestConfiguration
    class ReportServiceTestContextConfiguration {
        @Bean
        @Primary
        public ReportService reportService() {
            return new ReportService(reportRepo, transactionService, reportPartService, analysisService);
        }

        @Bean
        @Primary
        public AnalysisService analysisService() {
            return new AnalysisService(labelRepo, analysisRepo);
        }
    }

    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepo reportRepo;
    @MockBean
    private TransactionService transactionService;
    @Autowired
    private AnalysisService analysisService;
    @MockBean
    private ReportPartService reportPartService;
    @MockBean
    private TransactionRepo transactionRepo;
    @MockBean
    private LabelRepo labelRepo;
    @MockBean
    private AnalysisRepo analysisRepo;

    private CustomUser user;
    private Account account;
    private TimeSpanData time;
    private List<AbstractTransaction> transactionList;
    private Report report;

    @Before
    public void setUp() {
        user = new CustomUser("we@new.com", "qwerty", "name", "last", "middle");
        account = new DebitAccount("1", "debit", user, "testReport");
        time = new TimeSpanData(1, 2023);
        transactionList = new ArrayList<>() {
            {
                add(new PayTransaction(1000, "money", LocalDateTime.of(2023, 1, 2, 13, 20), "shop"));
            }
        };
        account.setTransactionList(transactionList);
        user.addAccount(account);
        report = reportService.create(user, time.getTimeStart(), time.getTimeEnd());
        Mockito.when(transactionService.getTransaction(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(transactionList);
    }

    @Test
    public void updateReportCheckDates() throws Exception {
        var report_time = report.getUpdated();
        var account2 = new DebitAccount("2", "debit", user, "testReport2");
        account2.AddTransaction(new PayTransaction(1000000, "money", LocalDateTime.now(), "dress"));
        user.addAccount(account2);
        var rep = reportService.updateReport(report, user);
        var check = false;
        if (rep.getUpdated().isAfter(report_time)) {
            check = true;
        }
        Assert.assertTrue(check);
    }

    @Test
    public void updateReportCheckComment() throws Exception {
        report.setComment("comment");
        var account2 = new DebitAccount("2", "debit", user, "testReport2");
        account2.AddTransaction(new PayTransaction(1000000, "money", LocalDateTime.now(), "dress"));
        user.addAccount(account2);
        var rep = reportService.updateReport(report, user);
        Assert.assertEquals("comment", rep.getComment());
    }

    @Test
    public void updateReportCheckBiggestPayment() throws Exception {
        var account2 = new DebitAccount("2", "debit", user, "testReport2");
        List<AbstractTransaction> transactionList2 = new ArrayList<>() {
            {
                add(new PayTransaction(1000000, "money", LocalDateTime.of(2023, 1, 11, 13, 20), "shop"));
            }
        };
        account2.setTransactionList(transactionList2);
        user.addAccount(account2);
        transactionList.addAll(transactionList2);
        var rep = reportService.updateReport(report, user);
        Assert.assertEquals(1000000, rep.getAnalysis().getBiggestPayment().getAmount(), 0.01);
    }

    @Test
    public void updateReportCheckFinalSum() throws Exception {
        List<AbstractTransaction> transactionList2 = new ArrayList<>() {
            {
                add(new PayTransaction(1000000, "money", LocalDateTime.of(2023, 1, 11, 13, 20), "shop"));
            }
        };
        transactionList.addAll(transactionList2);
        var rep = reportService.updateReport(report, user);
        Assert.assertEquals(1001000, rep.getAnalysis().getOther(), 0.01);
    }
}
