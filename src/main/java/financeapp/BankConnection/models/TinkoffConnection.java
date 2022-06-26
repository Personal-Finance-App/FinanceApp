package financeapp.BankConnection.models;

import financeapp.Accounts.models.Transaction;
import financeapp.BankConnection.Interface.BankConnection;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class TinkoffConnection extends BankConnection {
    @Override
    public List<Transaction> getTransactions(LocalDate fromDate) {
        return null;
    }
}
