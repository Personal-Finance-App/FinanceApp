package financeapp.bankConnection.fakeConnection.service;

import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.abstractFactory.FakeAccountManager;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeAccountFactory;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeCreditAccount;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeDebitAccount;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeSavingAccount;
import financeapp.bankConnection.fakeConnection.tools.RandomUtility;
import financeapp.monthReport.entity.Label;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import financeapp.users.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeConnectionService {
    private final int countTransaction;
    private final RandomUtility randomizer;
    private final UserService userService;
    private final AccountService accountService;
    private FakeAccountFactory factory;
    private final TransactionService transactionService;

    public FakeConnectionService(RandomUtility randomizer,
                                 AccountService accountService, UserService userService,
                                 TransactionService transactionService) {
        this.randomizer = randomizer;
        this.userService = userService;
        this.accountService = accountService;
        this.countTransaction = 50;
        this.transactionService = transactionService;
    }

    public List<Account> CreateAccounts(String email) {
        CustomUser user = userService.findUserByEmail(email);
        FakeAccountManager factory = new FakeAccountManager();
        List<Account> accounts = new ArrayList<>();
        accounts.add(factory.getCreditAccount(user));
        accounts.add(factory.getSavingAccount(user));
        accounts.add(factory.getDebitAccount(user));
        accountService.CreateAccountFromPayload(accounts, user);
        List<Account> accountList = addTransactionOnAccount(accounts);
        return accountList;
    }

    public Account CreateAccount(String type, String email) {
        CustomUser user = userService.findUserByEmail(email);
        if (type.equalsIgnoreCase("debit")) {
            factory = new FakeDebitAccount();
            return saveAccount(user);
        } else if (type.equalsIgnoreCase("credit")) {
            factory = new FakeCreditAccount();
            return saveAccount(user);
        } else if (type.equalsIgnoreCase("saving")) {
            factory = new FakeSavingAccount();
            return saveAccount(user);
        }
        throw new IllegalStateException("Unexpected value: " + RandomUtility.getRandomNumberForTypeTransaction());
    }


    public List<AbstractTransaction> AddTransactions(Account account) {
        List<Label> labels = new ArrayList<>();
        List<AbstractTransaction> abstractTransactions = new ArrayList<AbstractTransaction>();
        for (int i = countTransaction; i > 0; i--) {
            AbstractTransaction abstractTransaction = choiceTypeTransaction();
            abstractTransaction.setAmount(RandomUtility.getRandomNumberForMoneyTransaction());
            abstractTransaction.setDateTime(RandomUtility.getRandomLocalDateTime());
            abstractTransaction.setDescription("test transaction");
            abstractTransaction.setMerchant(RandomUtility.getRandomNameForMerchant());
            abstractTransaction.setCategory(randomizer.getRandomCategory());
            abstractTransaction.setLabelList(labels);
//            abstractTransaction.setAccount(account);
            abstractTransactions.add(abstractTransaction);
        }
        transactionService.saveTransactions(abstractTransactions, account, LocalDateTime.now());
//        account.setTransactionList(abstractTransactions);
        return abstractTransactions;
    }

    public AbstractTransaction choiceTypeTransaction() {
        var transaction = switch (RandomUtility.getRandomNumberForTypeTransaction()) {
            case 0 -> new IncomeTransaction();
            case 1 -> new PayTransaction();
            case 2 -> new TransferTransaction();
            default -> throw new IllegalStateException("Unexpected value: " + RandomUtility.getRandomNumberForTypeTransaction());
        };
        return transaction;
    }

    public List<Account> addTransactionOnAccount(List<Account> accounts) {
        for (Account account : accounts) {
            AddTransactions(account);
        }
        return accounts;
    }

    public Account saveAccount(CustomUser user) {
        Account account = factory.CreateAccount(user);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accountService.CreateAccountFromPayload(accounts, user);
        return account;
    }
}
