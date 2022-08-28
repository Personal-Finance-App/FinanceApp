package financeapp.financeGoal;

import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceGoalRepository extends JpaRepository<FinanceGoal, Long> {
    List<FinanceGoal> getFinanceGoalsByUser(CustomUser user);
    FinanceGoal getFinanceGoalById(Long id);
}
