package financeapp.Accounts.repositories;

import financeapp.Accounts.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    public Category findCategoryByCategoryName(String name);
}
