package financeapp.accounts.repositories;

import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<Account, UUID> {
    Account findAccountByIdInSystem(String id);
    Account findAccountById(UUID id);
    List<Account> findAccountByUser(CustomUser user);
}
