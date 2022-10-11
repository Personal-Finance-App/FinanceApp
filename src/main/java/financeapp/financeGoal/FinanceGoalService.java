package financeapp.financeGoal;


import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FinanceGoalService {
    private final FinanceGoalRepository financeGoalRepository;

    public FinanceGoal create(CustomUser user, SavingAccount account, String friendlyName, LocalDate achiveDate, double goalAmount) {
        var goal = new FinanceGoal(account, friendlyName, achiveDate, goalAmount, user);
        return financeGoalRepository.save(goal);
    }

    public FinanceGoal setImage(FinanceGoal goal, String url) {
        goal.setImageUrl(url);
        return financeGoalRepository.save(goal);
    }

    public void deleteGoal(Long id, CustomUser user) {
        var goal = financeGoalRepository.getFinanceGoalById(id);
        if (goal.getUser().getId().equals(user.getId()))
            financeGoalRepository.delete(goal);
        else
            throw new RuntimeException("Not yours goal");
    }

    public List<FinanceGoal> getAllByUser(CustomUser user) {
        return financeGoalRepository.getFinanceGoalsByUser(user);
    }
}
