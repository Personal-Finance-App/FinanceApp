package financeapp.bankConnection.fakeConnection.tools;

import financeapp.category.entity.Category;
import financeapp.category.repos.CategoryRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomUtility {

    private final CategoryRepo categoryRepo;

    public RandomUtility(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public static String getRandomNameForMerchant(){ // выглядит ущербно, но это единственное, что мне пришло в голову
        List<String> names = new ArrayList<String>();
        names.add("OZON4");
        names.add("SHERLOCK SHOP");
        names.add("AO ATK YAMAL");
        names.add("TABAKOFF");
        names.add("KRASNOE&BELOE");
        names.add("BURGER KING 0028");
        names.add("PAPIROSKA.RF");
        int random = new Random().nextInt(names.size());
        String name = names.get(random);
        return name;
    }

    public static int getRandomNumberForTypeTransaction()
    {
        return (int) (Math.random() * 3);
    }

    public static double  getRandomNumberForMoneyTransaction()
    {
        return (100 + Math.random() * 10000);
    }

    public Category getRandomCategory(){
        List<Category> categories = categoryRepo.findAll();
        int random = new Random().nextInt(categories.size());
        return categories.get(random);
    }

    public static LocalDate getRandomLocalDate(){
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomEpochDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public static LocalTime getRandomLocalTime(){
        LocalTime start = LocalTime.now().minusHours(1);
        LocalTime end = LocalTime.now();
        long startEpochDay = start.toSecondOfDay();
        long endEpochDay = end.toSecondOfDay();
        long randomEpochDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalTime.ofSecondOfDay(randomEpochDay);
    }

    public static LocalDateTime getRandomLocalDateTime(){
        LocalDate localDate = getRandomLocalDate();
        LocalTime localTime = getRandomLocalTime();
        return localDate.atTime(localTime);
    }
}
