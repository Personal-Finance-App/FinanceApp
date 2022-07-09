package financeapp.bankConnection.tinkoff.services;


import financeapp.bankConnection.tinkoff.TinkoffConnection;
import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApi;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApiFactory;
import financeapp.users.CustomUser;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class TinkoffService {


    @Autowired
    TinkoffConnectionRepo tinkoffConnectionRepo;

    TinkoffApi api = TinkoffApiFactory.getService();
    Logger logger = LoggerFactory.getLogger(TinkoffService.class);

    public String GetSession(CustomUser user) throws IOException {
        TinkoffConnection connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null) {
            connection = new TinkoffConnection("to-do");
            connection.setUser(user);
        }

        String deviceId = connection.getDeviceId();

        logger.debug("Request sessionId");
        var sessionId = api.getSession(
                        new FormBody.Builder().add("deviceId", deviceId).build())
                .execute().body().getPayload().getSessionid();
        logger.debug("Received SessionId: " + sessionId);

        if (sessionId == null)
            throw new RuntimeException("Can't get sessionId");
        connection.setActiveSessionId(sessionId);
        tinkoffConnectionRepo.save(connection);

        RequestBody dataForWarmUp = new FormBody.Builder()
                .add("deviceId", deviceId).build();

        var code = api.warmUpCache(sessionId, dataForWarmUp).execute().body().getResultCode();

        if (!Objects.equals(code, "OK")) {
            logger.error("Something went wrong");
            throw new RuntimeException("something went wrong");
        }
        logger.debug("Warming up session complete");
        return sessionId;

    }

    public String RegisterSendSms(String phone, CustomUser user) throws IOException {
        var sessionId = GetSession(user);
        TinkoffConnection connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        String deviceId = connection.getDeviceId();


        RequestBody dataForSmsRequest = new FormBody.Builder()
                .add("phone", phone)
                .add("deviceId", deviceId)
                .build();
        var smsResponse = api.requestSms(sessionId, dataForSmsRequest).execute();
        var operationTicket = smsResponse.body().getOperationTicket();

        var statusCode = smsResponse.body().getResultCode();

        if (statusCode == "REQUEST_RATE_LIMIT_EXCEEDED")
            throw new RuntimeException("REQUEST_RATE_LIMIT_EXCEEDED");

        if (operationTicket == null) {
            logger.error("SMS code doesn't sent! \n" + smsResponse.message());
            throw new RuntimeException("SMS code doesn't sent!");
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

    public void RegisterFinal(String operationTicket, String sms, String password, CustomUser user) throws IOException {
        TinkoffConnection connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null) {
            throw new RuntimeException("Can't find registred deviceId and Session");
        }

        String deviceId = connection.getDeviceId();
        String sessionId = connection.getActiveSessionId();

        String smsConfirm = "{\"SMSBYID\":\" " + sms + "\"}";
        logger.debug("For session " + sessionId + " and operation ticket " + operationTicket + "sending confirmation code");
        logger.debug("Confirmation Data " + smsConfirm);
        RequestBody confirmSmsData = new FormBody.Builder()
                .add("initialOperationTicket", operationTicket)
                .add("initialOperation", "\"auth/by/phone\"")
                .add("confirmationData", smsConfirm)
                .add("deviceId", deviceId).build();
        var response = api.confirmSms(sessionId, confirmSmsData).execute();
        logger.debug(response.body().getPayload().getKey());
        logger.debug(response.body().getPayload().getSsoId());

        var confirmPassword = api.
                confirmPassword(new FormBody.Builder()
                        .add("deviceId", deviceId)
                        .add("password", password).build(), sessionId).execute();

        logger.debug("Password confirmed");


        var pinCode = GeneratePinHash();
        var setDate = LocalDate.now().format(dateFormatter());
        var settingPinCode = api.
                setUpPin(new FormBody.Builder()
                                .add("deviceId", deviceId)
                                .add("pinHash", pinCode)
                                .add("auth_type_set_date", setDate)
                                .build(),
                        sessionId).execute();

        connection.setHashedPin(pinCode);
        connection.setPinSetDate(setDate);
        tinkoffConnectionRepo.save(connection);
        logger.debug("PinCode set and saved");
    }

    public String AuthWithPin(CustomUser user) throws IOException {
        var connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null)
            throw new RuntimeException("User must authorize first");

        if (connection.getHashedPin().isEmpty() || connection.getPinSetDate().isEmpty())
            throw new RuntimeException("User must login with sms first");

        var newSession = GetSession(user);

        var dataForAuth = new FormBody.Builder()
                .add("deviceId", connection.getDeviceId())
                .add("oldSessionId", connection.getActiveSessionId())
                .add("pinHash", connection.getHashedPin())
                .add("auth_type_set_date", connection.getPinSetDate())
                .add("auth_type", "pin")
                .build();

        var auth = api.loginByPinCode(newSession, dataForAuth);
        connection.setActiveSessionId(newSession);
        tinkoffConnectionRepo.save(connection);
        return newSession;
    }

}
