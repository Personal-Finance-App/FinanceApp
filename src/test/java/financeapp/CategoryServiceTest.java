package financeapp;


import financeapp.category.CategoryService;
import financeapp.category.entity.Category;
import financeapp.category.repos.CategoryRepo;
import financeapp.category.repos.MccRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

public class CategoryServiceTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @SpyBean
    private MccRepo mccRepo;

    @SpyBean
    private CategoryRepo categoryRepo;



    // Данные из InitializeCategory
    private final String categoryName = "Супермаркеты";

    @TestConfiguration
    class CategoryServiceTestContextConfiguration {
        @Bean
        public CategoryService categoryService() {
            return new CategoryService(categoryRepo, mccRepo);
        }
    }

    @Before
    public void setUp() {
        var category = new Category(1L, categoryName);
        Mockito.when(categoryRepo.findCategoryByCategoryName(categoryName)).thenReturn(category);
    }


    @Test
    public void createCategory_notSave() {
        // reset нужен для того, чтобы обнулить счетчик вызовов
        // Поскольку при инициализации создаются категории, он неправильно их считает без этой строки
        Mockito.reset(categoryRepo);
        var category = categoryService.getOrCreateCategory(categoryName);
        Assert.assertEquals(categoryName, category.getCategoryName());
        Mockito.verify(categoryRepo, Mockito.times(2)).findCategoryByCategoryName(categoryName);
        Mockito.verify(categoryRepo, Mockito.times(0)).save(category);
    }

    @Test
    public void createCategory_save() {
        // То же самое, что и наверху
        Mockito.reset(categoryRepo);
        Mockito.when(categoryRepo.findCategoryByCategoryName(categoryName)).thenReturn(null);
        var category = categoryService.getOrCreateCategory(categoryName);
        Assert.assertEquals(categoryName, category.getCategoryName());
        Mockito.verify(categoryRepo).findCategoryByCategoryName(categoryName);
        Mockito.verify(categoryRepo).save(category);
    }

    @Test
    public void convertMcc_receiveCategory() {
        String mccCode = "5411";
        var category = categoryService.convertMcc(mccCode);
        Assert.assertEquals(categoryName, category.getCategoryName());
    }

    @Test
    public void convertMcc_receiveDefaultCategory() {
        var category = categoryService.convertMcc("-0000");
        Assert.assertEquals("Без Категории", category.getCategoryName());
    }
}