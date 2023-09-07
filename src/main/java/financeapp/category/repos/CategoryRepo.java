package financeapp.category.repos;

import financeapp.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryName(String name);
    Category getById(Long id);
}
