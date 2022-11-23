package financeapp.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountData {
    private String name;
    private String id;
    private String AccountNumber;
    private String type;
    private Double balance;
}
