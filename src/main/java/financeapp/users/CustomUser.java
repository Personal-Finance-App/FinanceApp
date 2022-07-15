package financeapp.users;

import financeapp.accounts.models.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Класс пользователя. Вместо username я сразу использую email
 * Есть список счетов
 * TODO: роли
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
public class CustomUser implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String role;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> accountList;


    private String email;
    private String password;


    public CustomUser(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.role = "USER";
    }


    public boolean addAccount(Account account) {
        return this.accountList.add(account);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserWrapper toWrapper() {
        return new UserWrapper(this.email);
    }
}
