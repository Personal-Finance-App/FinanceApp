package financeapp.monthPlan;

import financeapp.monthPlan.models.MonthPlan;
import financeapp.monthPlan.models.PlanRepository;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public MonthPlan createPlan(MonthPlan plan, CustomUser user) {
        plan.setLinkedUser(user);
        return planRepository.save(plan);
    }

    public MonthPlan updatePlan(MonthPlan plan, CustomUser user) {
        if (!plan.getLinkedUser().getId().equals(user.getId()))
            throw new RuntimeException("Not your plan");
        var planFromDb = planRepository.getById(plan.getId());
        planFromDb = plan;
        return planRepository.save(planFromDb);
    }

    public MonthPlan getPlan(Long id) {
        return planRepository.getById(id);
    }
}
