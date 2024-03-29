package financeapp.accounts.services;

import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.users.CustomUser;
import financeapp.users.UserRepo;
import financeapp.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {
    private final AccountRepo accountRepo;
    private final UserService userService;

    public int CreateAccountFromPayload(List<Account> accounts, CustomUser user) {
        var accountToSave = new LinkedList<Account>();

        accounts.forEach(account ->
        {
            var found = accountRepo.findAccountByIdInSystem(account.getIdInSystem());
            if (found == null) {
                accountToSave.add(account);
                user.addAccount(account);
            }
        });
        userService.saveUser(user);
        if (accountToSave.size() > 0)
            accountRepo.saveAll(accountToSave);
        return accountToSave.size();
    }

    public Account getById(UUID id) {
        return accountRepo.findAccountById(id);
    }

    public List<Account> getAccountByUser(CustomUser user) {
        return accountRepo.findAccountByUser(user);
    }
    

    public Account getById(String id){
        return accountRepo.findAccountByIdInSystem(id);
    }

//    public void GetTransactions(Account account) {
//        List<Transaction> transactionList = account.getBankConnection().getTransactions(account.getLastSync());
//        transactionRepo.saveAll(transactionList);
//
//        //TODO: Установить категорию для транзакции
//        account.setLastSync(LocalDate.now());
//    }
    public List<Account> getAll(){
        return accountRepo.findAll();
    }

    public void deleteAccount(UUID id) {
        accountRepo.deleteById(id);
    }
}
