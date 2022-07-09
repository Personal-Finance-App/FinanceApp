package financeapp.bankConnection.tinkoff;

import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TinkoffConnectionRepo extends JpaRepository<TinkoffConnection, UUID> {
    TinkoffConnection findTinkoffConnectionByUser(CustomUser user);
}
