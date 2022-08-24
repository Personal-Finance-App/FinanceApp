package financeapp.bankConnection.sberbank.services;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.bankConnection.sberbank.SberbankConnectionRepo;
import financeapp.bankConnection.sberbank.api.calls.SberbankApi;
import financeapp.bankConnection.sberbank.api.calls.SberbankApiFactory;
import financeapp.bankConnection.sberbank.api.responseEntitys.AccountPayload;
import financeapp.bankConnection.sberbank.api.responseEntitys.transactionList.OperationType;
import financeapp.category.CategoryService;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SberbankService {

    private final SberbankConnectionRepo sberbankConnectionRepo;
    private final CategoryService categoryService;
    private final AccountRepo accountRepo;

    private DataForConnect privateLogin(CustomUser user) throws IOException {
        var result = new DataForConnect();
        var connection = sberbankConnectionRepo.getSberbankConnectionByUser(user);
        if (connection == null)
            throw new RuntimeException("Need to register connection first");
        var response = SberbankApiFactory.getService().login("button.login",
                connection.getPinCode(), connection.getMGUID()).execute();
        assert response.body() != null;
        if (response.body().getStatus().getCode() != 0)
            throw new RuntimeException("Some error occur");
        result.setHost(response.body().getLoginData().getHost());
        result.setToken(response.body().getLoginData().getToken());
        return result;
    }

    private Retrofit postCSALogin(DataForConnect data) throws IOException {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieHandler cookieHandler = new CookieManager();
        var result = new Retrofit.Builder()
                .baseUrl("https://" + data.getHost() + ":4477/")
                .addConverterFactory(JaxbConverterFactory.create())
                .client(
                        new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .cookieJar(new JavaNetCookieJar(cookieHandler))
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build()
                )
                .build();

        var api = result.create(SberbankApi.class);
        var response = api.postCSALogin(data.getToken()).execute();
        if (response.body().getStatus().getCode() != 0)
            throw new RuntimeException("Sberbank error API occur");

        return result;
    }

    private SberbankApi getApi(CustomUser user) throws IOException {
        var data = privateLogin(user);
        var result = postCSALogin(data);
        return result.create(SberbankApi.class);
    }


    public List<AccountPayload> getAccounts(CustomUser user) throws IOException {
        var api = getApi(user);
        var response = api.getAccounts("cards,accounts,imaccounts,loans").execute();
        assert response.body() != null;
        var result = new ArrayList<AccountPayload>();
        var accounts = response.body().getAccounts().getAccount();
        accounts.forEach(accountType -> {
            if(accountType.getState().equals("OPENED"))
            {
                result.add(new AccountPayload(
                        "saving",
                        (double) accountType.getBalance().getAmount(),
                        String.valueOf(accountType.getId()),
                        accountType.getName()
                ));
            }
        });
        var another = response.body().getCards().getCard();
        another.forEach(cardType -> {
            result.add(new AccountPayload(
                    cardType.getType(),
                    (double) cardType.getAvailableLimit().getAmount(),
                    String.valueOf(cardType.getId()),
                    cardType.getDescription()
            ));
        });
        return result;
    }

    public Account createAccountFromPayload(AccountPayload payload, CustomUser user) {
        var provider = "Сбербанк";
        var account = switch (payload.getType().toLowerCase(Locale.ROOT)) {
            case "debit" -> new DebitAccount(payload.getId(), payload.getFriendlyName(), user, provider);
            case "saving" -> new SavingAccount(payload.getId(), payload.getFriendlyName(), user, provider);
            case "credit" -> new CreditAccount(payload.getId(), payload.getFriendlyName(), user, provider);
            default -> throw new RuntimeException("Don't now this type of card: " + payload.getType());

        };
        account.setBalance(payload.getBalance());
        return account;
    }

    private String generateCardStr(Account account) {
        if (account instanceof DebitAccount)
            return "card:" + account.getIdInSystem();

        if (account instanceof SavingAccount)
            return "account:" + account.getIdInSystem();
        return "";
    }


    private AbstractTransaction createTransactionFromPayload(OperationType payload) {
        //13.07.2022T01:39:14
        var date = LocalDateTime.parse(payload.getDate(),
                DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm:ss"));

        var amount = Math.abs(payload.getOperationAmount().getAmount());


        // Такой треш..... столько типов существует...
        var result =  switch (payload.getForm().toLowerCase()) {
            case "p2psbpintransfer", "ufstransferself", "internalpayment",
                    "ufsp2psbpouttransfer", "ufsouttransfer", "extcardcashout" ->
                    new TransferTransaction(amount,
                            payload.getDescription(), date,  payload.getTo(), " ");
            case "extcardpayment", "takingmeans",
                    "extcardotherout", "ufsjuridicalpaymentlink", "rurpayjursb",
                    "ufssberpayconnect", "ufssberpayorder", "ufsqrsbp" ->
                    new PayTransaction(amount,
                            payload.getDescription(), date, payload.getTo());
            case "extcardcashin", "extcardtransferin",
                    "extdepositcapitalization" ->
                    new IncomeTransaction(amount,
                            payload.getDescription(),
                            date,
                            payload.getTo(),
                            payload.getTo());
            default -> null;
//            default -> throw new RuntimeException("Don't know this type of transaction " + payload.getForm());
        };

        if (result == null)
            LoggerFactory.getLogger(SberbankService.class).error("Don't know this type of transaction " + payload.getForm());

//        todo: set labdel transfer self
//        if (payload.getForm().toLowerCase().equals("ufstransferself") InternalPayment)
//            result.
        // Да тут можно столько лейблов расставить...
        if (result != null)
            result.setCategory(categoryService.convertMcc(payload.getClassificationCode()));
        return result;
    }

    public List<AbstractTransaction> getOperations(CustomUser user, Account account) throws IOException {
        var api = getApi(user);
        LocalDateTime startTime = account.getLastSync();
        if (startTime == null)
            startTime = LocalDateTime.now().minusMonths(6);

        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var startTimeStr = startTime.format(formatter);
        var endTimeStr = LocalDateTime.now().format(formatter);

        var transaction = api.getTransaction(startTimeStr,
                endTimeStr, generateCardStr(account), "500", "0", "true").execute();

        assert transaction.body() != null;
        // todo: add filter to state


        return transaction.body().getOperations()
                .getOperation()
                .stream()
                .map(this::createTransactionFromPayload)
                .toList();
    }

    public void updateCardsBalance(CustomUser user) throws IOException {
        var accounts = getAccounts(user);
        accounts.forEach(account -> {
                    var acc = accountRepo.findAccountByIdInSystem(account.getId());
                    if (acc != null)
                    {
                        acc.setBalance(account.getBalance());
                        accountRepo.save(acc);
                    }
                });
    }
}

@Data
class DataForConnect {
    private String host;
    private String token;
}
