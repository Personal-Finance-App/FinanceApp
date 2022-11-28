package financeapp.monthReport.entity;

import financeapp.category.entity.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@Getter
public class ReportCategoryPartResponse {
    private Category category;
    private List<LocalDate> dates;
    private List<Double> sums;
}
