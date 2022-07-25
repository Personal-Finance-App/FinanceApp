package financeapp.monthReport.repos;

import financeapp.monthReport.entity.ReportCategoryPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportPartRepo extends JpaRepository<ReportCategoryPart, Long> {
}
