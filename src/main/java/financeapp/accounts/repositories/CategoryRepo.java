package financeapp.accounts.repositories;

import financeapp.accounts.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Category findCategoryByCategoryName(String name);
}
