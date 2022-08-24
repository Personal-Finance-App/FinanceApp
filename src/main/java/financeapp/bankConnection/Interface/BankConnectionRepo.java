package financeapp.bankConnection.Interface;

import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankConnectionRepo extends JpaRepository<BankConnection, UUID> {
    List<BankConnection> getBankConnectionByUser(CustomUser user);
}
