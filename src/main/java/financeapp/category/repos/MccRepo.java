package financeapp.category.repos;

import financeapp.category.entity.MccToCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MccRepo extends JpaRepository<MccToCategory, Long> {
    MccToCategory findMccToCategoryByCode(String code);
}
