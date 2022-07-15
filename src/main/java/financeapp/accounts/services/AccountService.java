package financeapp.accounts.services;

import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.transaction.TransactionRepo;
import financeapp.users.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    public void CreateAccountFromPayload(AccountData data, CustomUser user) {
        Account account;
        String provider = "Тинькофф";
        // TODO: перенсти в сервис тинька
        account = switch (data.getType()) {
            case "Дебетовые карты" -> new DebitAccount(data.getId(), data.getName(), user, provider);
            case "Накопительные счета" -> new SavingAccount(data.getId(), data.getName(), user, provider);
            case "Кредитные карты" -> new CreditAccount(data.getId(), data.getName(), user, provider);
            default -> throw new RuntimeException("Что за тип счета?");
        };
        accountRepo.save(account);
        user.addAccount(account);


    }

//    public void GetTransactions(Account account) {
//        List<Transaction> transactionList = account.getBankConnection().getTransactions(account.getLastSync());
//        transactionRepo.saveAll(transactionList);
//
//        //TODO: Установить категорию для транзакции
//        account.setLastSync(LocalDate.now());
//    }
}
