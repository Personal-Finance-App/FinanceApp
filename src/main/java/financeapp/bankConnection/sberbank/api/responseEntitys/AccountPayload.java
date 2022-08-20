package financeapp.bankConnection.sberbank.api.responseEntitys;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountPayload {
    private String type;
    private Double balance;
    private String id;
    private String friendlyName;
}
