package financeapp.transaction;

import financeapp.accounts.models.Account;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends JpaRepository<AbstractTransaction, Long> {
    List<AbstractTransaction> getAllByDateTimeAfterAndDateTimeBeforeAndAccount(LocalDateTime after, LocalDateTime before, Account account);
}
