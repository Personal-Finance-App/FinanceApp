package financeapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class InitializeCategory {
    @Autowired
    private CategoryService categoryService;


    @PostConstruct
    public void runAfterStartup() {
        // TODO: больше категорий, за основу можно взять данные из тинькова
        var data = new HashMap<String, String>() {{
            put("9999", "Без Категории");
            put("6538", "Финан. услуги");
            put("0000", "Другое");
            put("5411", "Супермаркеты");
            put("5814", "Фастфуд");
            put("5993", "Разные товары");
            put("0004", "Мобильные/иб");
            put("0001", "Переводы/иб");
            put("5462", "Супермаркеты");
            put("5921", "Супермаркеты");
            put("5977", "Красота");
            put("4215", "Сервис. услуги");
            put("9311", "Гос. сборы");
            put("7523", "Транспорт");
            put("4814", "Связь, телеком");
            put("7230", "Красота");
            put("5947", "Сувениры");
            put("5331", "Разные товары");
            put("4121", "Транспорт");
            put("5651", "Одежда, обувь");
            put("5812", "Рестораны");
            put("5399", "Разные товары");
            put("8299", "Образование");
            put("5499", "Супермаркеты");
            put("5300", "Супермаркеты");
            put("5722", "Дом, ремонт");
            put("5311", "Разные товары");
            put("4511", "Авиабилеты");
            put("9402", "Гос. сборы");
            put("7922", "Развлечения");
            put("7210", "Сервис. услуги");
            put("5912", "Аптеки");
            put("7996", "Развлечения");
            put("5200", "Дом, ремонт");
        }};

        var buff = categoryService.getAll();
        if (buff.size() != data.size()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                categoryService.createCategory(entry.getValue(), entry.getKey());
            }
        }

    }
}

