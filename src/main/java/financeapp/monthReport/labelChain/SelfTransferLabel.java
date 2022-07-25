package financeapp.monthReport.labelChain;

import financeapp.monthReport.services.InitializeLabel;
import financeapp.transaction.models.AbstractTransaction;

import java.util.List;

/**
 * Метка "Перевод самому себе"
 *
 * @see InitializeLabel
 */

public class SelfTransferLabel extends Handler {

    @Override
    public void Handle(AbstractTransaction transaction, List<Integer> labels) {
        // todo: realize
        if (false) {
            labels.add(3);
        }

        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, labels);
    }
}
