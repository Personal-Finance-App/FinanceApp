package financeapp.transaction;

import financeapp.accounts.models.Account;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<AbstractTransaction, Long> {
    List<AbstractTransaction> getAllByDateTimeAfterAndDateTimeBeforeAndAccount(LocalDateTime after, LocalDateTime before, Account account);
    AbstractTransaction getById(Long id);
    Optional<AbstractTransaction> findById(Long id);
}
