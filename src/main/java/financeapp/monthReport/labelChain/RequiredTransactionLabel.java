package financeapp.monthReport.labelChain;

import financeapp.monthReport.services.InitializeLabel;
import financeapp.transaction.models.AbstractTransaction;

import java.util.List;


/**
 * Метка "Обязательный платеж"
 * Назначается, если категория относиться к оплате
 * ЖКХ, мобильной связи, интернета
 *
 * @see InitializeLabel
 */
public class RequiredTransactionLabel extends Handler {


    @Override
    public void Handle(AbstractTransaction transaction, List<Integer> labels) {
        var category = transaction.getCategory();
        if (category != null) {
            // todo: написать категории
            var categoryName = category.getCategoryName();
            if (categoryName.equals("ЖКХ") ||
                    categoryName.equals("Мобильные/иб") ||
                    categoryName.equals("Связь, телеком")) {
                labels.add(2);
            }
        }

        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, labels);
    }
}
