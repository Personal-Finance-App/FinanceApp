package financeapp.financeGoal;

import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.services.AccountService;
import financeapp.financeGoal.serializedEnitites.*;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Operation(description = "Minimum amount of money based on all goals")
    @GetMapping("/monthly-fee")
    public ResponseEntity<MonthlyFee> getMonthlyFee(Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var goals = financeGoalService.getAllByUser(user);
        var monthlyFee = goals.stream().collect(Collectors.summarizingDouble(FinanceGoal::MonthlyFee));
        return ResponseEntity.ok().body(MonthlyFee.builder().monthlyFee(monthlyFee.getSum()).build());
    }

}

