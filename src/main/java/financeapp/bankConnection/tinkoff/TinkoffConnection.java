package financeapp.bankConnection.tinkoff;

import financeapp.accounts.models.Transaction;
import financeapp.bankConnection.Interface.BankConnection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TinkoffConnection extends BankConnection {
    private String deviceId;
    private String activeSessionId;
    private String hashedPin;
    private String pinSetDate;

    public TinkoffConnection(String sessionId) {
        super();
        deviceId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        activeSessionId = sessionId;
    }


    @Override
    public List<Transaction> getTransactions(LocalDate fromDate) {
        return null;
    }
}
