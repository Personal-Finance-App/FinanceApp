package financeapp.transaction;

import financeapp.transaction.models.AbstractTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<AbstractTransaction, Long> {
}
