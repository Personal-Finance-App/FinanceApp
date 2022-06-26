package financeapp.BankConnection.Interface;

import financeapp.Accounts.models.Transaction;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Абстрактный класс, от которого можно будет наследоваться другим классам, которые отвечают за соединение с банком
 * На каждого пользователя создается отедельный класс, для соединения, которые хранит всю необходимую информацию,
 * чтобы получить список транзакций
 */
@Entity
@Getter
public abstract class BankConnection {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    public abstract List<Transaction> getTransactions(LocalDate fromDate);

}
