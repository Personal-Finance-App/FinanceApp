package financeapp.monthPlan;

import financeapp.monthPlan.models.MonthPlan;
import financeapp.monthPlan.models.PlanRepository;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PlanService {
    private final PlanRepository planRepository;

    public MonthPlan createPlan(MonthPlan plan, CustomUser user) {
        plan.setLinkedUser(user);
        return planRepository.save(plan);
    }

    public MonthPlan updatePlan(MonthPlan plan, CustomUser user) {
        var planFromDb = planRepository.getById(plan.getId());
        if (!planFromDb.getLinkedUser().getId().equals(user.getId()))
            throw new RuntimeException("Not yours plan");
        planFromDb = plan;
        planFromDb.setLinkedUser(user);
        return planRepository.save(planFromDb);
    }

    public MonthPlan getPlan(Long id) {
        return planRepository.getById(id);
    }

    public List<MonthPlan> monthPlansByUser(CustomUser user) {
        return planRepository.getByLinkedUser(user);
    }
}
