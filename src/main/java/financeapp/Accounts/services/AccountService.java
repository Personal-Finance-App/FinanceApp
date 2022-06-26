package financeapp.Accounts.services;

import financeapp.Accounts.models.Account;
import financeapp.Accounts.models.Transaction;
import financeapp.Accounts.repositories.AccountRepo;
import financeapp.Accounts.repositories.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    public void GetTransactions(Account account) {
        List<Transaction> transactionList = account.getBankConnection().getTransactions(account.getLastSync());
        transactionList.forEach(transaction -> {
            transactionRepo.save(transaction);
        });

        //TODO: Установить категорию для транзакции
        account.setLastSync(LocalDate.now());
    }
}
