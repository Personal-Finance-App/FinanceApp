package financeapp.monthReport.repos;

import financeapp.monthReport.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepo extends JpaRepository<Analysis, Long> {
}
