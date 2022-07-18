package financeapp.bankConnection.tinkoff.services;


import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.bankConnection.tinkoff.TinkoffConnection;
import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApi;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApiFactory;
import financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList.AccountPayload;
import financeapp.category.CategoryService;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.IncomeTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.models.TransferTransaction;
import financeapp.transaction.services.TransactionPattern;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TinkoffService {


    @Autowired
    TinkoffConnectionRepo tinkoffConnectionRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CategoryService categoryService;

    TinkoffApi api = TinkoffApiFactory.getService();
    Logger logger = LoggerFactory.getLogger(TinkoffService.class);

    /**
     * Функция, которая запрашивает новую сессию.
     * Я так понимаю, ей лучше не злоупотреблять, и если она остается не незавершенной то мб банят
     *
     * @param user Пользователь
     * @return ID сессии
     */
    public String GetSession(CustomUser user) throws IOException, RuntimeException {
        TinkoffConnection connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null) {
            connection = new TinkoffConnection("to-do");
            connection.setUser(user);
        }

        String deviceId = connection.getDeviceId();

        logger.debug("Request sessionId");
        var sessionId = Objects.requireNonNull(api.getSession(deviceId, deviceId)
                .execute().body()).getPayload().getSessionid();
        logger.debug("Received SessionId: " + sessionId);

        if (sessionId == null)
            throw new RuntimeException("Невозможно получить sessionId");
        connection.setActiveSessionId(sessionId);
        tinkoffConnectionRepo.save(connection);


        var code = Objects.requireNonNull(api.warmUpCache(sessionId, deviceId, deviceId).execute().body()).getResultCode();

        if (!Objects.equals(code, "OK")) {
            throw new RuntimeException("Что-то пошло не так =(");
        }
        logger.debug("Warming up session complete");
        return sessionId;

    }

    /**
     * Запрос регистрации <br>
     * На этом этапе отправляется код на номер телефона
     *
     * @param phone номер телефона в формате +7**********
     * @param user  Пользьватель
     * @return OperationTicketId - ID запроса, на который нужено будет ответить
     */
    public String RegisterSendSms(String phone, CustomUser user) throws IOException, RuntimeException {
        var sessionId = GetSession(user);
        TinkoffConnection connection = getConnection(user);
        String deviceId = connection.getDeviceId();

        var smsResponse = api.requestSms(sessionId, phone, deviceId, deviceId).execute();
        assert smsResponse.body() != null;
        var operationTicket = smsResponse.body().getOperationTicket();

        var statusCode = smsResponse.body().getResultCode();

        if (statusCode.equals("REQUEST_RATE_LIMIT_EXCEEDED"))
            throw new RuntimeException("Превышен лимит запросов к серверу Тинькофф");

        if (statusCode.equals("INVALID_REQUEST_DATA"))
            throw new RuntimeException("Некорректный номер телефона");

        if (operationTicket == null) {
            throw new RuntimeException("СМС Код не отправлен!");
        }
        logger.debug("SMS code have been sent, received operationTicket: " + operationTicket);
        return operationTicket;
    }

    private String GeneratePinHash() {
        return UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
    }

    private DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Завершение регистрации сессии
     *
     * @param operationTicket ID запроса, на который нужно ответить
     * @param sms             смс код от пользотваеля
     * @param password        пароль от онлайн банка
     * @param user            Пользотваель
     */
    public void RegisterFinal(String operationTicket, String sms, String password, CustomUser user) throws IOException {
        TinkoffConnection connection = getConnection(user);
        String deviceId = connection.getDeviceId();
        String sessionId = connection.getActiveSessionId();

        String smsConfirm = "{\"SMSBYID\": \"" + sms + "\"}";
        logger.debug("For session " + sessionId + " and operation ticket " + operationTicket + "sending confirmation code");
        logger.debug("Confirmation Data " + smsConfirm);
        var response = api.confirmSms(sessionId, deviceId, deviceId, operationTicket, "auth/by/phone", smsConfirm).execute();
        assert response.body() != null;

        if (!response.body().getResultCode().equals("OK"))
            throw new RuntimeException("Ответ от сервера Тинькофф: " + response.body().getResultCode());

        if (!response.body().getPayload().getAccessLevel().equals("CANDIDATE"))
            throw new RuntimeException("Что-то пошло не так - " + response.body().getResultCode());
        logger.debug("Сессия потверждена СМС Кодом");
        logger.debug("Попытка отправить пароль");

        var confirmPassword = api.
                confirmPassword(sessionId, deviceId, deviceId, password).execute();

//        if (!response.body().getPayload().getAccessLevel().equals("CLIENT"))
//            throw new RuntimeException("Что-то пошло не так");

        logger.debug("Пароль потвержден");
        logger.debug("Попытка установить пин-код");


        var pinCode = GeneratePinHash();
        var setDate = LocalDateTime.now().format(dateFormatter());
        var settingPinCode = api.
                setUpPin(sessionId, deviceId, deviceId, pinCode, setDate).execute();

        connection.setHashedPin(pinCode);
        connection.setPinSetDate(setDate);
        tinkoffConnectionRepo.save(connection);
        logger.debug("Пин код установлен и сохранен в базу данных");
    }

    /**
     * Аутенфикация с помощью пин-кода <br>
     *
     * @param user Пользователь
     * @return ID новой сессии
     */
    public String AuthWithPin(CustomUser user) throws IOException {
        var connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null)
            throw new RuntimeException("User must authorize first");

        if (connection.getHashedPin().isEmpty() || connection.getPinSetDate().isEmpty())
            throw new RuntimeException("User must login with sms first");

        var oldSession = connection.getActiveSessionId();
        var newSession = GetSession(user);


        var auth = api.loginByPinCode(newSession,
                connection.getDeviceId(),
                connection.getDeviceId(),
                oldSession,
                connection.getHashedPin(),
                connection.getPinSetDate(),
                "pin").execute();

        assert auth.body() != null;
        if (auth.body().getResultCode().equals("WRONG_PIN_CODE")) {
            throw new RuntimeException("Неверный пин-код");
            // удалить аккаунт и попросить заново зарегестрироваться?
        }

        logger.info(auth.body().getPayload().getAccessLevel());
        connection.setActiveSessionId(newSession);
        tinkoffConnectionRepo.save(connection);
        return newSession;
    }

    private TinkoffConnection getConnection(CustomUser user) {
        var connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null)
            throw new RuntimeException("Пользователя не существует. Сначала нужно зарегистрироваться");

        return connection;
    }

    private String getSession(CustomUser user) throws IOException {
        var connection = getConnection(user);
        var pingResult = api.pingSession(connection.getActiveSessionId(), connection.getDeviceId(), connection.getDeviceId()).execute();
        assert pingResult.body() != null;
        var accessLevel = pingResult.body().getPayload().getAccessLevel();

        if (accessLevel.equals("CANDIDATE")) {
            // Возможно удалить все данные, которые есть?
            // Потому что этот статус появляется только после ввода смс кода и перед вводом пароля
            // Тут он возникнуть ну никак не может
            throw new RuntimeException("Вам необходимо пройти регистрацию заново");
        }

        if (accessLevel.equals("ANONYMOUS"))
            return AuthWithPin(user);

        return connection.getActiveSessionId();
    }

    public List<AccountPayload> getAccounts(CustomUser user) throws IOException {
        var sessionId = getSession(user);

        var connection = getConnection(user);
        if (connection.getHashedPin().isEmpty() || connection.getPinSetDate().isEmpty())
            throw new RuntimeException("Пользователь сначала должен зарегистрироваться и авторизоваться");
        var accounts = api.accountsList(connection.getActiveSessionId(),
                connection.getDeviceId(),
                connection.getDeviceId()).execute();

        assert accounts.body() != null;
        return accounts.body().getPayload();
    }

    public AbstractTransaction createTransaction(TransactionPattern data) {
        AbstractTransaction newTransaction;
        LocalDateTime time = Instant.ofEpochMilli(data.getDateTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();


        newTransaction = switch (data.getType().toLowerCase()) {
            case "pay" -> new PayTransaction(data.getAmount(), data.getDescription(), time, data.getMerchantName());
            case "income", "correction" -> new IncomeTransaction(data.getAmount(), data.getDescription(),
                    time, data.getMerchantName(), data.getSendDetails());
            case "transfer", "cash" -> new TransferTransaction(data.getAmount(), data.getDescription(), time, data.getMerchantName(), data.getMessage());
            default -> throw new RuntimeException("Don't now type of transaction :" + data.getType());
        };

        //Определение категории
        var category = categoryService.GetOrCreateCategoryByMccCode(data.getMccString(), data.getCategoryName());
        newTransaction.setCategory(category);

        return newTransaction;
    }

    public List<AbstractTransaction> getOperations(CustomUser user, Account account) throws IOException {
        var sessionId = getSession(user);
        var connection = getConnection(user);
        LocalDateTime startTime = account.getLastSync();
        if (startTime == null)
            startTime = LocalDateTime.now().minusMonths(6);

        ZonedDateTime zdt = ZonedDateTime.of(startTime, ZoneId.systemDefault());
        var resultTime = zdt.toInstant().toEpochMilli();

        LocalDateTime syncTime = LocalDateTime.now();

        var operations = api.transactionList(connection.getDeviceId(),
                connection.getDeviceId(), sessionId, account.getIdInSystem(), String.valueOf(resultTime)).execute();

        List<AbstractTransaction> result = new ArrayList<>();

        assert operations.body() != null;
        operations.body().getPayload().forEach(transactionPayload -> {
            if (transactionPayload.getStatus().equals("OK")) {
                TransactionPattern data = new TransactionPattern();
                data.setAmount(transactionPayload.getAmount());
                data.setDateTime(Long.valueOf(transactionPayload.getDateTimeMilliSeconds()));
                data.setDescription(transactionPayload.getDescription());
                data.setCategoryName(transactionPayload.getCategoryName());
                data.setMccString(transactionPayload.getMccString());
                data.setMessage(transactionPayload.getMessage());
                data.setMerchantName(transactionPayload.getMerchantName());
                data.setType(transactionPayload.getGroup());
                data.setSendDetails(transactionPayload.getSenderDetails());
                result.add(this.createTransaction(data));
            }
        });


        return result;


    }

}
