package financeapp.transaction.services;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionPattern {
    private double amount;
    private String description;
    private Long dateTime;
    private String categoryName;
    private String merchantName;
    private String mccString;
    private String type;
    private String sendDetails;
    private String message;

}
