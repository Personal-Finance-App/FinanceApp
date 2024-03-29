package financeapp.users;

import financeapp.accounts.models.Account;
import io.swagger.v3.oas.models.info.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Account> accountList;


    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;

    public CustomUser(String email, String password, String firstName, String lastName, String middleName) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.accountList = new ArrayList<>();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }



    //extendable
    public boolean checkName(String data) {
        // Ivanov I.
        if (data.equalsIgnoreCase(lastName + " " + firstName.charAt(0) + "."))
            return true;

        // Ivan I.
        if (data.equalsIgnoreCase(firstName + " " + lastName.charAt(0) + "."))
            return true;

        // Ivan Yovanovitch I.
        return data.equalsIgnoreCase(firstName + " " + middleName + " " + lastName.charAt(0) + ".");
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
