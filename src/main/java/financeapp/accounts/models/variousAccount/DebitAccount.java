package financeapp.accounts.models.variousAccount;

import financeapp.accounts.models.Account;
import financeapp.users.CustomUser;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class DebitAccount extends Account {

    public DebitAccount(String id, String name, CustomUser user, String provider) {
        super(id, name, user, provider);
    }

    @Override
    public String getName() {
        return "Дебетовый счет";
    }
}
