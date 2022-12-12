package financeapp.financeGoal;

import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.services.AccountService;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/goals")
@Tag(name = "Finance Goal")
@SecurityRequirement(name = "javainuseapi")
public class FinanceGoalController {

    private final UserRepo userRepo;
    private final AccountService accountService;
    private final FinanceGoalService financeGoalService;

    @PostMapping("/create")
    public ResponseEntity<?> createGoal(Authentication authentication, @RequestBody DataForGoal data) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var acc = accountService.getById(data.getAccountId());
        if (!(acc instanceof SavingAccount))
            throw new RuntimeException("Not saving account");

        if (acc.getUser().getId() != user.getId())
            throw new RuntimeException("Not your account");

        var parsedDate = LocalDate.parse(data.getDate());


        var goal = financeGoalService.create(user, (SavingAccount) acc,
                data.getFriendlyName(), parsedDate, data.getGoalAmount());

        if (!data.getUrl().isEmpty())
            financeGoalService.setImage(goal, data.getUrl());
        return ResponseEntity.ok().body("created");


    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteGoal(Authentication authentication, @RequestBody RemoveData data) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        financeGoalService.deleteGoal(data.getGoalId(), user);
        return ResponseEntity.ok().body(data.getGoalId());
    }
//
//    public ResponseEntity<?> loadAllGoals(Authentication authentication) {
//
//    };

}

@Data
class DataForGoal {
    private String friendlyName;
    private double goalAmount;
    private String date;
    private UUID accountId;
    private String url;
}

@Data
class RemoveData {
    private Long goalId;
}