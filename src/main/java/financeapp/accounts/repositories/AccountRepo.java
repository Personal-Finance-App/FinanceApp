package financeapp.accounts.repositories;

import financeapp.accounts.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<Account, UUID> {
    Account findAccountByIdInSystem(String id);
}
