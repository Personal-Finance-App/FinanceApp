package financeapp.bankConnection.fakeConnection.factoryMethod;

import financeapp.accounts.models.Account;
import financeapp.users.CustomUser;

public interface FakeAccountFactory {
    Account CreateAccount(CustomUser user);
}
