package financeapp.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<CustomUser, UUID> {
    public CustomUser findUserByUsername(String username);
}
