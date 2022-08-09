package financeapp.monthReport.analysisChain;

import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.entity.Label;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;


/**
 * Считаю доходом все входящие транзакции, кроме переводов самому себе
 */
public class CountIncomeHandler extends AnalysisHandler {

    public CountIncomeHandler(Label label) {
        super(label);
    }

    @Override
    public void Handle(AbstractTransaction transaction, Analysis analysis) {
        if (!transaction.getLabelList().contains(getLabel()) &&
                transaction instanceof IncomeTransaction) {
            analysis.setIncome(analysis.getIncome() + transaction.getAmount());
            return;
        }

        if (getNextHandler() != null)
            getNextHandler().Handle(transaction, analysis);
    }
}
