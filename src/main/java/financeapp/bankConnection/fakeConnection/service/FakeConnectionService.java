package financeapp.bankConnection.fakeConnection.service;

import financeapp.accounts.AccountData;
import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.category.entity.Category;
import financeapp.category.repos.CategoryRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.users.CustomUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class FakeConnectionService {
    private final int countTransaction;
    private final CategoryRepo categoryRepo;

    public FakeConnectionService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
        this.countTransaction = 50;
    }

    public Account createDebitAccount(CustomUser user){
        String provider = null;
        UUID id = UUID.randomUUID();
        DebitAccount debitAccount = new DebitAccount(id.toString(), getRandomNameForUser(), user, provider);
        return debitAccount;
    }

    public Account createSavingAccount(CustomUser user){
        String provider = null;
        UUID id = UUID.randomUUID();
        SavingAccount savingAccount = new SavingAccount(id.toString(), getRandomNameForUser(), user, provider);
        return savingAccount;
    }

    public Account createCreditAccount(CustomUser user){
        String provider = null;
        UUID id = UUID.randomUUID();
        CreditAccount creditAccount = new CreditAccount(id.toString(), getRandomNameForUser(), user, provider);
        return creditAccount;
    }


    public List<AbstractTransaction> addTransactions(){
        List<AbstractTransaction> abstractTransactions = new ArrayList<AbstractTransaction>();
        for(int i = countTransaction; i > 0; i--){
            AbstractTransaction abstractTransaction = choiceTypeTransaction();
            abstractTransaction.setAmount(getRandomNumberForMoneyTransaction());
            abstractTransaction.setDateTime(getRandomLocalDateTime());
            abstractTransaction.setDescription("test transaction");
            abstractTransaction.setMerchant("none");
            abstractTransaction.setCategory(getRandomCategory());
            abstractTransactions.add(abstractTransaction);
        }
        return abstractTransactions;
    }

    public AbstractTransaction choiceTypeTransaction(){
        var transaction = switch (getRandomNumberForTypeTransaction()){
            case 0 -> new IncomeTransaction();
            case 1 -> new PayTransaction();
            case 2 -> new TransferTransaction();
            default -> throw new IllegalStateException("Unexpected value: " + getRandomNumberForTypeTransaction());
        };
        return transaction;
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

    public static String getRandomNameForUser(){
        List<String> names = new ArrayList<String>();
        names.add("Vasya");
        names.add("Dima");
        names.add("Nikita");
        names.add("Misha");
        names.add("Lesha");
        names.add("Rustam");
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
