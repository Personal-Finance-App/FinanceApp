package financeapp.monthReport.services;

import financeapp.monthReport.analysisChain.CountIncomeHandler;
import financeapp.monthReport.analysisChain.CountRequieredTransactionHandler;
import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.repos.AnalysisRepo;
import financeapp.monthReport.repos.LabelRepo;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {
    private final LabelRepo labelRepo;
    private final AnalysisRepo analysisRepo;

    public AnalysisService(LabelRepo labelRepo, AnalysisRepo analysisRepo) {
        this.labelRepo = labelRepo;
        this.analysisRepo = analysisRepo;
    }

    public Analysis startAnalysis(List<AbstractTransaction> transactionList) {
        var income = new CountIncomeHandler(labelRepo.findLabelByName("Перевод самому себе"));
        var requirePay = new CountRequieredTransactionHandler(labelRepo.findLabelByName("Обязательный платеж"));
        var skipLabel = labelRepo.findLabelByName("Не учитывать");
        var analysis = new Analysis();
        // цепочка обработки
        income.setNext(requirePay);

        for (AbstractTransaction transaction :
                transactionList) {
            if (!transaction.getLabelList().contains(skipLabel)) {
                income.Handle(transaction, analysis);
            }
        }

        analysisRepo.save(analysis);
        return analysis;
    }

    ;
}
