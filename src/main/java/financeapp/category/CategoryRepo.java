package financeapp.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Category getCategoryByMccCode(String mccCode);

    Category findCategoryByMccCode(String mccCode);
}
