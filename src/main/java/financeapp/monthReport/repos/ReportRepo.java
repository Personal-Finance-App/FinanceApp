package financeapp.monthReport.repos;

import financeapp.monthReport.entity.Report;
import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Long> {
    Report findReportByLinkedUserAndMonthAndYear(CustomUser user, Integer month, Integer year);

    Report findReportByIdAndLinkedUser(Long id, CustomUser user);
    List<Report> findReportByLinkedUser(CustomUser user);
    void deleteReportByLinkedUserAndMonthAndYear(CustomUser user, Integer month, Integer year);
    void deleteReportById(Long id);
}
