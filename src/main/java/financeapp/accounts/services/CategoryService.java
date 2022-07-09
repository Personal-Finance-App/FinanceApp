package financeapp.accounts.services;

import financeapp.accounts.models.Category;
import financeapp.accounts.repositories.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category associateCategory(String placeName) {
        // Можно использовать либо сторонний сервис
        // Либо же собирать свою какую-то базу ассоциаций имен с категорией
        return null;
    }
}