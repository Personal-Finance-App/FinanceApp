package financeapp.Users;

import financeapp.Accounts.models.Account;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
public class CustomUser implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> accountList;

    private String username;
    private String password;
    private String email;


    public CustomUser(String username, String email) {
        this.username = username;
        this.email = email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
        return false;
    }
}
