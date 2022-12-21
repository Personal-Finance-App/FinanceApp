package financeapp.monthPlan.models;

import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<MonthPlan, Long> {
    MonthPlan getById(Long id);
    List<MonthPlan> getByLinkedUser(CustomUser user);
}
