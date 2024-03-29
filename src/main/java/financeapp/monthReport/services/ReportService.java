package financeapp.monthReport.services;

import financeapp.category.entity.Category;
import financeapp.monthReport.TimeSpanData;
import financeapp.monthReport.entity.Report;
import financeapp.monthReport.entity.ReportCategoryPartResponse;
import financeapp.monthReport.repos.ReportRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class ReportService {
    private final ReportRepo reportRepo;
    private final TransactionService transactionService;
    private final ReportPartService reportPartService;
    private final AnalysisService analysisService;

    public Report create(CustomUser user, LocalDateTime start, LocalDateTime end) {
        Report rep = reportRepo.findReportByLinkedUserAndMonthAndYear(user, start.getMonthValue(), start.getYear());
        if (rep != null){
            reportRepo.deleteReportByLinkedUserAndMonthAndYear(user, start.getMonthValue(), start.getYear());

        }
        var accounts = user.getAccountList();
        var syncTime = LocalDateTime.now();
        List<AbstractTransaction> transactionList = new ArrayList<>();
        accounts.forEach(account -> {
            var operations = transactionService.getTransaction(account, start, end);
            transactionList.addAll(operations);
        });
        var analysis = analysisService.startAnalysis(transactionList);
        var parts = reportPartService.sortTransactions(transactionList);

        var newReport = new Report(user, start.getYear(), start.getMonthValue(), parts, syncTime);

        newReport.setAnalysis(analysis);
        reportRepo.save(newReport);
        return newReport;

    }

    public Report updateReport(Report report, CustomUser user) throws Exception {
        if (report == null){
            throw new Exception("Отчета не существует!");
        }
//        var user = report.getLinkedUser();
        var accounts = report.getLinkedUser().getAccountList();
        var comments = report.getComment();
        Integer year = report.getYear();
        Integer month = report.getMonth();
        TimeSpanData data = new TimeSpanData(month, year);
        reportRepo.deleteReportById(report.getId());
        var syncTime = LocalDateTime.now();
        List<AbstractTransaction> transactionList = new ArrayList<>();
        accounts.forEach(account -> {
            var operations = transactionService.getTransaction(account, data.getTimeStart(), data.getTimeEnd());
            transactionList.addAll(operations);
        });
        var analysis = analysisService.startAnalysis(transactionList);
        var parts = reportPartService.sortTransactions(transactionList);

        var newReport = new Report(user, year, month, parts, syncTime);
        if (comments != null)
            newReport.setComment(comments);

        newReport.setAnalysis(analysis);
        reportRepo.save(newReport);
        return newReport;
    }


    public Report findReport(CustomUser user, Integer month, Integer year) throws Exception {
        var report = reportRepo.findReportByLinkedUserAndMonthAndYear(user, month, year);
        if (report == null)
            throw new Exception("Отчет еще не создан!");
        return report;
    }

    public boolean setComment(Report report, String comment) {
        report.setComment(comment);
        reportRepo.save(report);
        return true;
    }

    public ReportCategoryPartResponse getCategoriesHistory(CustomUser user, Long category, Integer len) {
        var reports = reportRepo.findReportByLinkedUser(user);
        if (reports.size() > len)
            reports = reports.subList(0, len + 1);


        var result = ReportCategoryPartResponse.builder();
        ArrayList<LocalDate> dates = new ArrayList<>();
        var sums = new ArrayList<Double>();
        final Category[] categoryObj = {null};
        reports.forEach(report -> {
            var date = LocalDate.of(report.getYear(), report.getMonth(), 1);
            var part = report
                    .getParts()
                    .stream()
                    .filter(i -> i.getCategory().getId().equals(category))
                    .findFirst();

            if (part.isPresent()) {
                dates.add(date);
                sums.add(part.get().getSum());
                categoryObj[0] = part.get().getCategory();
            }

        });

        return result
                .sums(sums)
                .dates(dates)
                .category(categoryObj[0])
                .build();
    }


    public List<Report> findReportsByUserPeriod(CustomUser user, Integer length) {
        LocalDate now = LocalDate.now().minusMonths(1);
        List<Report> reports = new ArrayList<>();
        for (var i = 0; i < length; i++) {
            var report = reportRepo.findReportByLinkedUserAndMonthAndYear(user, now.getMonthValue(), now.getYear());
            if (report != null)
                reports.add(report);
            System.out.println(now.getMonthValue());
            System.out.println(now.getYear());
            now = now.minusMonths(1);
        }
        return reports;
    }

}
