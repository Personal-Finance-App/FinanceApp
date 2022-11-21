package financeapp.bankConnection.fakeConnection.abstractFactory;

import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.users.CustomUser;

public interface FactoryAccountManager {
    CreditAccount getCreditAccount(CustomUser user);
    DebitAccount getDebitAccount(CustomUser user);
    SavingAccount getSavingAccount(CustomUser user);
}
