package financeapp.monthReport.services;

import financeapp.monthReport.entity.ReportCategoryPart;
import financeapp.monthReport.repos.ReportPartRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.users.CustomUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReportPartService {
    private final ReportPartRepo reportPartRepo;

    public ReportPartService(ReportPartRepo reportPartRepo) {
        this.reportPartRepo = reportPartRepo;
    }

    public List<ReportCategoryPart> sortTransactions(List<AbstractTransaction> transactionList) {
        var parts = new HashMap<Long, ReportCategoryPart>();

        for (AbstractTransaction element : transactionList) {
            if (element instanceof PayTransaction) {
                var category = element.getCategory();
                var part = parts.get(category.getId());
                if (part == null) {
                    part = new ReportCategoryPart(category);
                    parts.put(category.getId(), part);
                }
                part.AddTransaction(element);
            }
        }

        var result = parts.values().stream().toList();
        reportPartRepo.saveAll(result);
        return result;
    }




}
