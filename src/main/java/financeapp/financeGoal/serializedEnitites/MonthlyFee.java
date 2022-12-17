package financeapp.financeGoal.serializedEnitites;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthlyFee {
    private Double monthlyFee;
}
