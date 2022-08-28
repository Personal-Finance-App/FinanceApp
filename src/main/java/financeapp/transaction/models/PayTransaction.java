package financeapp.transaction.models;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class PayTransaction extends AbstractTransaction {
    public PayTransaction(double amount, String description, LocalDateTime dateTime, String merchant) {
        super(amount, description, dateTime, merchant);
    }

    @Override
    public String friendName() {
        return "Расход";
    }

    public PayTransaction() {
        super();
    }
}
