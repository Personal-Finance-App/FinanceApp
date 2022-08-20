package financeapp.monthReport.services;

import financeapp.monthReport.analysisChain.*;
import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.repos.AnalysisRepo;
import financeapp.monthReport.repos.LabelRepo;
import financeapp.transaction.models.AbstractTransaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        var maxTransaction = new MaxTransactionHandler();
        var savedAmount = new SavedAmountHandler();
        var other = new OtherHandler(); // ВСЕГДА ПОСЛЕДНИМ ИДЕТ
        var skipLabel = labelRepo.findLabelByName("Не учитывать");
        var selfTransfer = labelRepo.findLabelByName("Перевод самому себе");
        var analysis = new Analysis();

        // цепочка обработки
        income.setNext(requirePay).setNext(maxTransaction).setNext(savedAmount).setNext(other);

        for (AbstractTransaction transaction :
                transactionList) {
            if (!transaction.getLabelList().contains(skipLabel) && !transaction.getLabelList().contains(selfTransfer)) {
                income.Handle(transaction, analysis);
            }
        }

        analysis.setAveragePerDay(CountAveragePerDay(transactionList));
        analysisRepo.save(analysis);
        return analysis;
    }


    private double CountAveragePerDay(List<AbstractTransaction> transactionList) {
        Map<LocalDateTime, List<AbstractTransaction>> groupByDate =
                transactionList.stream().collect(Collectors.groupingBy(AbstractTransaction::getDateTime));

        List<Double> averagePerDay = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<AbstractTransaction>> entry : groupByDate.entrySet()) {
            var average = entry.getValue().stream().mapToDouble(AbstractTransaction::getAmount).sum();
            averagePerDay.add(average);
        }
        var size = averagePerDay.size();
        return averagePerDay.stream().mapToDouble(Double::doubleValue).sorted()
                .skip((size - 1) / 2)
                .limit(2 - size % 2)
                .average().orElse(Double.NaN);

    }

}
