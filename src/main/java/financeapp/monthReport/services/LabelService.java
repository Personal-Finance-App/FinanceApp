package financeapp.monthReport.services;

import financeapp.monthReport.entity.Label;
import financeapp.monthReport.labelChain.RequiredTransactionLabel;
import financeapp.monthReport.labelChain.SelfTransferLabel;
import financeapp.monthReport.repos.LabelRepo;
import financeapp.transaction.models.AbstractTransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {
    private final LabelRepo labelRepo;

    public Label createLabel(String name) {
        var label = labelRepo.findLabelByName(name);
        if (label == null)
            return labelRepo.save(new Label(name));
        return null;
    }

    public List<Label> getAll() {
        return labelRepo.findAll();
    }

    public void setLabels(AbstractTransaction transaction) {
        var handle = new RequiredTransactionLabel();
        handle.setNext(new SelfTransferLabel());

        var labels = new ArrayList<Integer>();
        handle.Handle(transaction, labels);

        labels.forEach(num -> {
            var found = labelRepo.findLabelByName(
                    InitializeLabel.getLabels().get(num)
            );
            transaction.addLabel(found);
        });

    }
}
