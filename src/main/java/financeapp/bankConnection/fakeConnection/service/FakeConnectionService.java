package financeapp.bankConnection.fakeConnection.service;

import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.users.CustomUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FakeConnectionService {
    private final int countTransaction;

    public FakeConnectionService() {
        this.countTransaction = 50;
    }

    public Account createAccount(AccountData accountData, CustomUser user){
        String provider = null;
        var account = switch (accountData.getType().toLowerCase(Locale.ROOT)) {
            case "дебетовые карты" -> new DebitAccount(accountData.getId(), accountData.getName(), user, provider);
            case "накопительные счета" -> new SavingAccount(accountData.getId(), accountData.getName(), user, provider);
            case "кредитные карты" -> new CreditAccount(accountData.getId(), accountData.getName(), user, provider);
            default -> throw new RuntimeException("Don't now this type of card: " + accountData.getType());
        };
        return account;
    }

    public List<AbstractTransaction> addTransactions(){
        List<AbstractTransaction> abstractTransactions = new ArrayList<AbstractTransaction>();
        for(int i = countTransaction; i > 0; i--){
            AbstractTransaction abstractTransaction = choiceTypeTransaction();
            abstractTransaction.setAmount(getRandomNumberForMoneyTransaction());
            abstractTransaction.setDateTime(LocalDateTime.now());
            abstractTransaction.setDescription("test transaction");
            abstractTransaction.setMerchant("none");
            abstractTransactions.add(abstractTransaction);
        }
        return abstractTransactions;
    }

    public AbstractTransaction choiceTypeTransaction(){
        var transaction = switch (getRandomNumberForTypeTransaction()){
            case 0 -> new IncomeTransaction();
            case 1 -> new PayTransaction();
            case 2 -> new TransferTransaction();
            default -> throw new IllegalStateException("Unexpected value: " + getRandomNumberForTypeTransaction());
        };
        return transaction;
    }

    public static int getRandomNumberForTypeTransaction()
    {
        return (int) (Math.random() * 3);
    }

    public static double  getRandomNumberForMoneyTransaction()
    {
        return (100 + Math.random() * 10000);
    }

}
