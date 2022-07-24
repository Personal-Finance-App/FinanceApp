package financeapp.monthReport.repos;

import financeapp.monthReport.entity.ReportPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportPartRepo extends JpaRepository<ReportPart, Long> {
}
