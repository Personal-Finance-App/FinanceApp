package financeapp.monthPlan;

import financeapp.monthPlan.models.MonthPlan;
import financeapp.users.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
@Tag(name = "Month Plan")
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final UserService userService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<MonthPlan> createPlan (@RequestBody MonthPlan monthPlan,
                                         Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        var plan = planService.createPlan(monthPlan, user);
        return ResponseEntity.ok().body(plan);
    }

    @PutMapping("/edit")
    @ResponseBody
    public ResponseEntity<MonthPlan> updatePlan (@RequestBody MonthPlan monthPlan,
                                                 Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        var plan = planService.createPlan(monthPlan, user);
        return ResponseEntity.ok().body(plan);
    }

    @GetMapping("/get/{planId}")
    @ResponseBody
    public ResponseEntity<MonthPlan> getPlan(Authentication authentication, @PathVariable(name = "planId") Long planId) {
        var user = userService.findUserByEmail(authentication.getName());
        var plan = planService.getPlan(planId);
        if (!plan.getLinkedUser().getId().equals(user.getId()))
            throw new RuntimeException("Not yours plan");
        return ResponseEntity.ok().body(plan);
    }
}
