package financeapp.BankConnection.models;

import financeapp.Accounts.models.Transaction;
import financeapp.BankConnection.Interface.BankConnection;
import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Фейковое соединение, которое позволяет получить 30 транзакций со случайной суммой
 */
@Entity
@Getter
public class FakeConnection extends BankConnection {
    @Override
    public List<Transaction> getTransactions(LocalDate fromDate) {
        List<Transaction> result = new ArrayList<>();
        var random = new Random();
        for (int i = 0; i < 30; i++){
            result.add(new Transaction(random.nextInt(100, 50000), "TestPlace", LocalDate.now()));
        }
        return result;
    }
}
