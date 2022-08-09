package financeapp.monthReport.analysisChain;

import financeapp.monthReport.entity.Analysis;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;

public class MaxTransactionHandler extends AnalysisHandler {

    public MaxTransactionHandler() {
        super(null);
    }

    @Override
    public void Handle(AbstractTransaction transaction, Analysis analysis) {

        if (transaction instanceof PayTransaction) {
            if (analysis.getBiggestPayment() == null) {
                analysis.setBiggestPayment((PayTransaction) transaction);
                return;
            }
            if (analysis.getBiggestPayment().getAmount() < transaction.getAmount()) {
                analysis.setBiggestPayment((PayTransaction) transaction);
                return;
            }
        }


        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, analysis);
    }
}
