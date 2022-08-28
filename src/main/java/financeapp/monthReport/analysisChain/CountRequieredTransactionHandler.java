package financeapp.monthReport.analysisChain;

import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.entity.Label;
import financeapp.transaction.models.AbstractTransaction;

public class CountRequieredTransactionHandler extends AnalysisHandler {


    public CountRequieredTransactionHandler(Label label) {
        super(label);
    }

    @Override
    public void Handle(AbstractTransaction transaction, Analysis analysis) {
        if (transaction.getLabelList().contains(getLabel())) {
            analysis.setRequirePayments(analysis.getRequirePayments() + transaction.getAmount());
            return;
        }

        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, analysis);
    }
}
