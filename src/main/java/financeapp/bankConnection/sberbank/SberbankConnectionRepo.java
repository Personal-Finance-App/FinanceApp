package financeapp.bankConnection.sberbank;

import financeapp.users.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SberbankConnectionRepo extends JpaRepository<SberbankConnection, UUID> {
    SberbankConnection getSberbankConnectionByUser(CustomUser user);
}
