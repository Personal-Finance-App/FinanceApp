package financeapp.monthReport.wrappers;

import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.entity.ReportCategoryPart;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReportWrapper {
    private Integer year;
    private Integer month;
    private String updated;
    private List<ReportPartWrapper> parts;
    private String categoryMaxName;
    private Analysis analysis;

    public ReportWrapper(Integer year, Integer month, LocalDateTime updated, List<ReportCategoryPart> parts, Analysis analysis) {
        this.year = year;
        this.month = month;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.updated = updated.format(formatter);
        this.parts = parts.stream().map(part -> part.toWrapper()).collect(Collectors.toList());
        this.analysis = analysis;
        this.categoryMaxName = this.parts.stream()
                .collect(Collectors.maxBy(
                        Comparator.comparingDouble(ReportPartWrapper::getTransactionSum)))
                .get()
                .getCategoryName();
    }

}
