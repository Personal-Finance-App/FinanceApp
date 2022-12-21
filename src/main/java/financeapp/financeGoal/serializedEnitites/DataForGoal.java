package financeapp.financeGoal.serializedEnitites;

import lombok.Data;

import java.util.UUID;

@Data
public
class DataForGoal {
    private String friendlyName;
    private double goalAmount;
    private String date;
    private UUID accountId;
    private String url;
}
