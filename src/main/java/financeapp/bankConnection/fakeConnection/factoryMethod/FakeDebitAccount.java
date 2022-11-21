package financeapp.bankConnection.fakeConnection.factoryMethod;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.users.CustomUser;

import java.util.UUID;

public class FakeDebitAccount implements FakeAccountFactory{
    @Override
    public Account CreateAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new DebitAccount(id.toString(), "TestDebit", user, provider);
    }
}
