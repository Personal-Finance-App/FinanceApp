package financeapp.monthReport.labelChain;

import financeapp.accounts.models.Account;
import financeapp.monthReport.services.InitializeLabel;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.users.CustomUser;

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
//        if (transaction.getDescription().equals("Перевод между счетами")) {
//            labels.add(3);
//        }
        Account account = transaction.getAccount();
        CustomUser customUser = account.getUser();
        switch (transaction.getDescription()) {
            case "Перевод между своими счетами",
                    "Перевод между счетами",
                    "Регулярный перевод в Инвесткопилку" -> labels.add(3);
        }


        boolean isSelf = transaction.getMerchant() != null && customUser.checkName(transaction.getMerchant());

        if (transaction.getDescription() != null && customUser.checkName(transaction.getDescription()))
            isSelf = true;

        if (transaction instanceof IncomeTransaction buf) {
            if (buf.getSenderDetails() != null && customUser.checkName(buf.getSenderDetails()))
                isSelf = true;
        }


        if (isSelf)
            labels.add(3);

        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, labels);
    }
}
