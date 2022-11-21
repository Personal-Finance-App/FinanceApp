package financeapp.bankConnection.fakeConnection.abstractFactory;

import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.users.CustomUser;

import java.util.UUID;

public class FakeAccountManager implements FactoryAccountManager {

    @Override
    public CreditAccount getCreditAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new CreditAccount(id.toString(), "TestCredit", user, provider);
    }

    @Override
    public DebitAccount getDebitAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new DebitAccount(id.toString(), "TestDebit", user, provider);
    }

    @Override
    public SavingAccount getSavingAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new SavingAccount(id.toString(), "TestSaving", user, provider);
    }
}
