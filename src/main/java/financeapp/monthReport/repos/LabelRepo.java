package financeapp.monthReport.repos;

import financeapp.monthReport.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepo extends JpaRepository<Label, Long> {
    Label findLabelByName(String name);

    Label findLabelById(Long id);
}
