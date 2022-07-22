package financeapp.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category createCategory(String categoryName) {
        var category = categoryRepo.findCategoryByCategoryName(categoryName);
        if (category != null)
            throw new CategoryExistsException();

        var newCategory = new Category(categoryName);
        categoryRepo.save(newCategory);
        return newCategory;
    }

    public Category ConvertMcc(String mccCode) {
        return null;
    }


}
