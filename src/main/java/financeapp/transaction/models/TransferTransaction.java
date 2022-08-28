package financeapp.transaction.models;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class TransferTransaction extends AbstractTransaction {
    private String message;

    public TransferTransaction(double amount, String description, LocalDateTime dateTime, String merchant, String message) {
        super(amount, description, dateTime, merchant);
        this.message = message;
    }

    public TransferTransaction() {

    }

    @Override
    public String friendName() {
        return "Перевод";
    }
}
