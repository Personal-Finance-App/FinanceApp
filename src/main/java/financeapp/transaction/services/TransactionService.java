package financeapp.transaction.services;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;

    /**
     * Метод для сохранения транзакций <br>
     * здесь же они проходят валидацию
     *
     * @param operations список операций (которые заранее были созданы в Income/Pay/Transfer)
     * @param account    счет
     * @param syncTime   время сохранения (синхронизации)
     * @return количество сохраненных операций
     */
    public int saveTransactions(List<AbstractTransaction> operations, Account account, LocalDateTime syncTime) {


        transactionRepo.saveAll(operations);


        account.setLastSync(syncTime);
        accountRepo.save(account);
        return operations.size();
    }
}
