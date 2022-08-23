package financeapp.bankConnection.fakeConnection.factoryMethod;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.users.CustomUser;

import java.util.UUID;

public class FakeCreditAccount implements FakeAccountFactory{

    @Override
    public Account CreateAccount(CustomUser user) {
        String provider = "Fake";
        UUID id = UUID.randomUUID();
        return new CreditAccount(id.toString(), "TestCredit", user, provider);

    }
}
