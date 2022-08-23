package financeapp.bankConnection.fakeConnection.service;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.abstractFactory.FactoryAccountManager;
import financeapp.bankConnection.fakeConnection.abstractFactory.FakeAccountManager;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeAccountFactory;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeCreditAccount;
import financeapp.bankConnection.fakeConnection.factoryMethod.FakeDebitAccount;
import financeapp.bankConnection.fakeConnection.tools.RandomUtility;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.users.CustomUser;
import financeapp.users.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FakeConnectionService {
    private final int countTransaction;
    private final RandomUtility randomizer;
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;
    private FakeAccountFactory factory;
    private final TransactionRepo transactionRepo;
    @Autowired
    private AccountService accountService;

    public FakeConnectionService(RandomUtility randomizer, AccountRepo accountRepo, UserRepo userRepo,
                                 TransactionRepo transactionRepo) {
        this.randomizer = randomizer;
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.countTransaction = 50;
        this.transactionRepo = transactionRepo;
    }

    public List<Account> CreateAccounts(String email){
        CustomUser user = userRepo.findCustomUserByEmail(email);
        FakeAccountManager factory = new FakeAccountManager();
        List<Account> accounts = new ArrayList<>();
        accounts.add(factory.getCreditAccount(user));
        accounts.add(factory.getDebitAccount(user));
        accounts.add(factory.getSavingAccount(user));
        List<Account> accountList = addTransactionOnAccount(accounts);
        accountRepo.saveAll(accountList);
        return accountList;
    }

    public Account CreateAccount(String type, String email){
        CustomUser user = userRepo.findCustomUserByEmail(email);
        if(type.equalsIgnoreCase("debit")){
            factory = new FakeDebitAccount();
            return saveAccount(user);
        } else if(type.equalsIgnoreCase("credit")){
            factory = new FakeCreditAccount();
            return saveAccount(user);
        } else if(type.equalsIgnoreCase("saving")){
            return saveAccount(user);
        }
        throw new IllegalStateException("Unexpected value: " + RandomUtility.getRandomNumberForTypeTransaction());
    }


    public List<AbstractTransaction> addTransactions(String id){
        Account account = accountRepo.findAccountByIdInSystem(id);
        List<AbstractTransaction> abstractTransactions = new ArrayList<AbstractTransaction>();
        List<AbstractTransaction> abstractTransactionList = account.getTransactionList();
        for(int i = countTransaction; i > 0; i--){
            AbstractTransaction abstractTransaction = choiceTypeTransaction();
            abstractTransaction.setAmount(RandomUtility.getRandomNumberForMoneyTransaction());
            abstractTransaction.setDateTime(RandomUtility.getRandomLocalDateTime());
            abstractTransaction.setDescription("test transaction");
            abstractTransaction.setMerchant(RandomUtility.getRandomNameForMerchant());
            abstractTransaction.setCategory(randomizer.getRandomCategory());
            abstractTransactions.add(abstractTransaction);
            abstractTransactionList.add(abstractTransaction);
        }
        transactionRepo.saveAll(abstractTransactions);
        account.setTransactionList(abstractTransactionList);
        return abstractTransactions;
    }

    public AbstractTransaction choiceTypeTransaction(){
        var transaction = switch (RandomUtility.getRandomNumberForTypeTransaction()){
            case 0 -> new IncomeTransaction();
            case 1 -> new PayTransaction();
            case 2 -> new TransferTransaction();
            default -> throw new IllegalStateException("Unexpected value: " + RandomUtility.getRandomNumberForTypeTransaction());
        };
        return transaction;
    }
    public List<Account> addTransactionOnAccount(List<Account> accounts){
        for(Account account : accounts){
            addTransactions(account.getIdInSystem());
        }
        return accounts;
    }
    public Account saveAccount(CustomUser user){
        Account account = factory.CreateAccount(user);
        accountRepo.save(account);
        return account;
    }
}
