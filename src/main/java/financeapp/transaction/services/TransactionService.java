package financeapp.transaction.services;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.category.CategoriesList;
import financeapp.category.entity.Category;
import financeapp.monthReport.entity.Label;
import financeapp.monthReport.services.LabelService;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;
    private final LabelService labelService;

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
        operations.forEach(transaction ->
        {
            transaction.setAccount(account);
            labelService.setLabels(transaction);
        });

        transactionRepo.saveAll(operations);
        account.setLastSync(syncTime);
        accountRepo.save(account);

        return operations.size();
    }

    public List<AbstractTransaction> getTransaction(Account account, LocalDateTime after, LocalDateTime before) {
        return transactionRepo.getAllByDateTimeAfterAndDateTimeBeforeAndAccount(after, before, account);
    }

    public List<PayTransaction> getTransactionWithoutCategory(Account account, LocalDateTime after, LocalDateTime before) {
        var transaction = getTransaction(account, after, before);
        return transaction.stream()
                .filter(tr -> tr.getCategory().getCategoryName().equals(CategoriesList.DEFAULT.label))
                .filter(tr -> tr instanceof PayTransaction)
                .map(tr -> (PayTransaction)tr )
                .toList();
    }

    public AbstractTransaction getTransaction(Long id) {
        var transaction = transactionRepo.findById(id);
        if (transaction.isEmpty())
            throw new RuntimeException("Transaction with ID=" + id + " does not exist");
        return transaction.get();
    }

    public AbstractTransaction setCategory(AbstractTransaction transaction, Category category) {
        transaction.setCategory(category);
        return transactionRepo.save(transaction);
    }

    public AbstractTransaction save(AbstractTransaction transaction) {
        return transactionRepo.save(transaction);
    }

}
