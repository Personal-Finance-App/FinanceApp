package financeapp.bankConnection.fakeConnection.factoryMethod;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.users.CustomUser;

import java.util.UUID;

public class FakeSavingAccount implements FakeAccountFactory{
    @Override
    public Account CreateAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new SavingAccount(id.toString(), "TestSaving", user, provider);
    }
}
