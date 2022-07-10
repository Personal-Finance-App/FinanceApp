package financeapp.bankConnection.tinkoff.services;


import financeapp.bankConnection.tinkoff.TinkoffConnection;
import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApi;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApiFactory;
import financeapp.users.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TinkoffService {


    @Autowired
    TinkoffConnectionRepo tinkoffConnectionRepo;

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

        logger.debug("Сессия потверждена СМС Кодом");
        logger.debug("Попытка отправить пароль");

        var confirmPassword = api.
                confirmPassword(sessionId, deviceId, deviceId, password).execute();

        logger.debug("Пароль потвержден");
        logger.debug("Попытка установить пин-код");


        var pinCode = GeneratePinHash();
        var setDate = LocalDate.now().format(dateFormatter());
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

        var newSession = GetSession(user);


        var auth = api.loginByPinCode(newSession,
                connection.getDeviceId(),
                connection.getDeviceId(),
                connection.getActiveSessionId(),
                connection.getHashedPin(),
                connection.getPinSetDate(),
                "pin");
        connection.setActiveSessionId(newSession);
        tinkoffConnectionRepo.save(connection);
        return newSession;
    }

    private TinkoffConnection getConnection(CustomUser user) {
        var connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null)
            throw new RuntimeException("Пользователя не существует. Сначала нужно зарегистрироваться");

        if (connection.getHashedPin().isEmpty() || connection.getPinSetDate().isEmpty())
            throw new RuntimeException("Пользователь сначала должен зарегистрироваться и авторизоваться");
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

    public List<?> getAccounts(CustomUser user) throws IOException {
        var sessionId = getSession(user);
        var connection = getConnection(user);
        var accounts = api.accountsList(connection.getActiveSessionId(),
                connection.getDeviceId(),
                connection.getDeviceId()).execute();

        // TODO: parse accounts

        //TODO: create account if in not db ?

        return null;
    }

//    public List<?> getOperations(CustomUser user, Account account) throws IOException {
//        var sessionId = getSession(user);
//        var connection = getConnection(user);
//
//        var operations = api.accountsList(sessionId, connection.getDeviceId(), connection.getDeviceId());
//
//
//    }

}
