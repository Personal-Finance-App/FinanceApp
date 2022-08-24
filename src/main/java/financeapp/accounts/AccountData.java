package financeapp.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountData {
    private String name;
    private String id;
    private String externalAccountNumber;
    private String type;
    private Double balance;
}
