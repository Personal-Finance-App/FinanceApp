package financeapp.transaction.models;

import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class IncomeTransaction extends AbstractTransaction {

    private String senderDetails;

    public IncomeTransaction(double amount, String description, LocalDateTime dateTime, String merchant, String senderDetails) {
        super(amount, description, dateTime, merchant);
        this.senderDetails = senderDetails;
    }

    public IncomeTransaction() {

    }

    @Override
    public String friendName() {
        return "Доход";
    }
}
