package financeapp.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category GetOrCreateCategoryByMccCode(String mccCode, String categoryName) {
        var category = categoryRepo.findCategoryByMccCode(mccCode);
        if (category == null) {
            category = createCategory(mccCode, categoryName);
        }

        return category;
    }

    public Category createCategory(String mccCode, String categoryName) {
        var category = categoryRepo.findCategoryByMccCode(mccCode);
        if (category != null)
            throw new CategoryExistsException();

        var newCategory = new Category(categoryName, mccCode);
        categoryRepo.save(newCategory);
        return newCategory;
    }


}
