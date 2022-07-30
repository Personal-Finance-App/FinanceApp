package financeapp.monthReport.repos;

import financeapp.monthReport.entity.Report;
import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Long> {
    Report findReportByLinkedUserAndMonthAndYear(CustomUser user, Integer month, Integer year);
}
