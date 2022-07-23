package financeapp.category;

import financeapp.category.entity.Category;
import financeapp.category.entity.MccToCategory;
import financeapp.category.repos.CategoryRepo;
import financeapp.category.repos.MccRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final MccRepo mccRepo;
    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepo categoryRepo, MccRepo mccRepo) {
        this.categoryRepo = categoryRepo;
        this.mccRepo = mccRepo;
    }

    public Category createCategory(String categoryName) {
        var category = categoryRepo.findCategoryByCategoryName(categoryName);
        if (category != null)
            throw new CategoryExistsException();

        var newCategory = new Category(categoryName);
        categoryRepo.save(newCategory);
        return newCategory;
    }

    public Category getOrCreateCategory(String categoryName) {
        try {
            return createCategory(categoryName);
        } catch (CategoryExistsException e) {
            return categoryRepo.findCategoryByCategoryName(categoryName);
        }
    }

    public Category createCategory(String categoryName, String mccCode) {
        var category = getOrCreateCategory(categoryName);
        var found = mccRepo.findMccToCategoryByCode(mccCode);
        if (found != null) {
            return category;
        }
        var converter = new MccToCategory(mccCode);
        converter.setLinkCategory(category);
        mccRepo.save(converter);
        return category;

    }

    public Category convertMcc(String mccCode) {
        var found = mccRepo.findMccToCategoryByCode(mccCode);
        if (found == null) {
            found = mccRepo.findMccToCategoryByCode("9999");
            logger.error("MCC код " + mccCode + " не найден");
        }
        return found.getLinkCategory();
    }


    public List<Category> getAll() {
        return categoryRepo.findAll();
    }


}
