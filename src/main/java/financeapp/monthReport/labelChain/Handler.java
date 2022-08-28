package financeapp.monthReport.labelChain;

import financeapp.transaction.models.AbstractTransaction;
import lombok.Getter;

import java.util.List;

/**
 * Для установки метки нужно написать обработчик транзакции
 *
 * @see RequiredTransactionLabel пример
 * @see financeapp.monthReport.services.LabelService установить обработчик здесь
 */
@Getter
public abstract class Handler {
    private Handler nextHandler;

    public Handler setNext(Handler next) {
        nextHandler = next;
        return next;
    }

    public abstract void Handle(AbstractTransaction transaction, List<Integer> labels);
}
