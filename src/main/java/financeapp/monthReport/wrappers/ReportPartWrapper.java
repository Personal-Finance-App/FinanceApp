package financeapp.monthReport.wrappers;

import financeapp.category.entity.Category;
import financeapp.transaction.models.AbstractTransaction;
import lombok.Data;

import java.util.List;

@Data
public class ReportPartWrapper {
    private String categoryName;
    private Double transactionSum;

    public ReportPartWrapper(Category category, List<AbstractTransaction> transactionList) {
        categoryName = category.getCategoryName();
        transactionSum = transactionList.stream()
                .map(transaction -> transaction.getAmount())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
