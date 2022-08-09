package financeapp.monthReport.analysisChain;

import financeapp.monthReport.entity.Analysis;
import financeapp.transaction.models.AbstractTransaction;

public class OtherHandler extends AnalysisHandler {
    public OtherHandler() {
        super(null);
    }

    @Override
    public void Handle(AbstractTransaction transaction, Analysis analysis) {
        analysis.setOther(analysis.getOther() + transaction.getAmount());

    }
}
