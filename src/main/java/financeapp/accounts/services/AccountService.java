package financeapp.accounts.services;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {
    private final AccountRepo accountRepo;

    public int CreateAccountFromPayload(List<Account> accounts) {


        accountRepo.saveAll(accounts);
        return accounts.size();


    }

//    public void GetTransactions(Account account) {
//        List<Transaction> transactionList = account.getBankConnection().getTransactions(account.getLastSync());
//        transactionRepo.saveAll(transactionList);
//
//        //TODO: Установить категорию для транзакции
//        account.setLastSync(LocalDate.now());
//    }
}
