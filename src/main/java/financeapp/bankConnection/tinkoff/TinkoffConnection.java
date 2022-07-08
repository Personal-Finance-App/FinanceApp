package financeapp.bankConnection.tinkoff;

import financeapp.accounts.models.Transaction;
import financeapp.bankConnection.Interface.BankConnection;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class TinkoffConnection extends BankConnection {


    private String deviceId;
    private String activeSessionId;


    @Override
    public List<Transaction> getTransactions(LocalDate fromDate) {
        return null;
    }
}
