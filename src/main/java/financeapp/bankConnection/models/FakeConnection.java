package financeapp.bankConnection.models;

import financeapp.bankConnection.Interface.BankConnection;
import lombok.Getter;

import javax.persistence.Entity;

/**
 * Фейковое соединение, которое позволяет получить 30 транзакций со случайной суммой
 */
@Entity
@Getter
public class FakeConnection extends BankConnection {

}
