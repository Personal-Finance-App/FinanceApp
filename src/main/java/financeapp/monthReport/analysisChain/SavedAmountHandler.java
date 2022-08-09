package financeapp.monthReport.analysisChain;

import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.monthReport.entity.Analysis;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;

public class SavedAmountHandler extends AnalysisHandler {
    public SavedAmountHandler() {
        super(null);
    }

    @Override
    public void Handle(AbstractTransaction transaction, Analysis analysis) {
        if (transaction.getAccount() instanceof SavingAccount) {
            var saved = analysis.getSaved();
            if (transaction instanceof IncomeTransaction)
                analysis.setSaved(saved + transaction.getAmount());
            else
                analysis.setSaved(saved - transaction.getAmount());
            return;
        }


        if (this.getNextHandler() != null)
            this.getNextHandler().Handle(transaction, analysis);
    }
}
