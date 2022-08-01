package financeapp.monthReport.analysisChain;

import financeapp.monthReport.entity.Analysis;
import financeapp.monthReport.entity.Label;
import financeapp.transaction.models.AbstractTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Цепочка обязанностей для анализа траннзакции
 * <p>
 * // * @see RequiredTransactionLabel пример
 * // * @see financeapp.monthReport.services.LabelService установить обработчик здесь
 */
@Getter
@AllArgsConstructor
public abstract class AnalysisHandler {

    private AnalysisHandler nextHandler;
    private Label label;

    public AnalysisHandler(Label label) {
        this.label = label;
    }

    public AnalysisHandler setNext(AnalysisHandler next) {
        nextHandler = next;
        return next;
    }

    public abstract void Handle(AbstractTransaction transaction, Analysis analysis);

}
