package financeapp.monthReport.services;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeLabel {

    private final LabelService labelService;

    // Не менять порядок!
    private final static List<String> labels = new ArrayList<>() {{
        add("Не учитывать");
        add("Обязательный платеж");
        add("Перевод самому себе");
    }};

    public static List<String> getLabels() {
        return labels;
    }

    public InitializeLabel(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostConstruct
    public void runAfterStartup() {
        var buff = labelService.getAll();
        if (buff.size() != labels.size()) {
            for (String entry : labels) {
                labelService.createLabel(entry);
            }
        }

    }
}
