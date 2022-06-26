package financeapp.Accounts.repositories;

import financeapp.Accounts.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepo extends JpaRepository<Transaction, UUID> {
}
