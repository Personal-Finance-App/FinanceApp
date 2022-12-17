package financeapp.monthPlan.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<MonthPlan, Long> {
    MonthPlan getById(Long id);
}
