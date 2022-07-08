package financeapp.bankConnection.models;

import financeapp.accounts.models.Transaction;
import financeapp.bankConnection.Interface.BankConnection;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class SberBankConnection extends BankConnection {
    @Override
    public List<Transaction> getTransactions(LocalDate fromDate) {
        return null;
    }
}
