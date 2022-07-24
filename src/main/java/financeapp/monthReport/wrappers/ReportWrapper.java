package financeapp.monthReport.wrappers;

import financeapp.monthReport.entity.ReportPart;
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

    public ReportWrapper(Integer year, Integer month, LocalDateTime updated, List<ReportPart> parts) {
        this.year = year;
        this.month = month;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.updated = updated.format(formatter);
        this.parts = parts.stream().map(part -> part.toWrapper()).collect(Collectors.toList());

        this.categoryMaxName = this.parts.stream()
                .collect(Collectors.maxBy(
                        Comparator.comparingDouble(ReportPartWrapper::getTransactionSum)))
                .get()
                .getCategoryName();
    }

}