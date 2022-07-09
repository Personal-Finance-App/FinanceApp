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
import java.util.Objects;

@Service
public class TinkoffService {


    @Autowired
    TinkoffConnectionRepo tinkoffConnectionRepo;

    TinkoffApi api = TinkoffApiFactory.getService();
    Logger logger = LoggerFactory.getLogger(TinkoffService.class);

    public String RegisterSendSms(String phone, CustomUser user) throws IOException {
        TinkoffConnection connection = tinkoffConnectionRepo.findTinkoffConnectionByUser(user);
        if (connection == null) {
            connection = new TinkoffConnection("to-do");
            connection.setUser(user);
        }

        String deviceId = connection.getDeviceId();


        logger.debug("Request sessionId");
        var sessionId = api.getSession(deviceId).execute().body().getPayload().getSessionid();
        logger.debug("Received SessionId: " + sessionId);

        RequestBody dataForWarmUp = new FormBody.Builder().add("deviceId", deviceId).build();

        var code = api.warmUpCache(sessionId, dataForWarmUp).execute().body().getResultCode();

        if (!Objects.equals(code, "OK")) {
            logger.error("Something went wrong");
            throw new RuntimeException("something went wrong");
        }
        logger.debug("Warming up session complete");


        if (sessionId == null)
            throw new RuntimeException("Can't get sessionId");
        connection.setActiveSessionId(sessionId);
        tinkoffConnectionRepo.save(connection);


        RequestBody dataForSmsRequest = new FormBody.Builder()
                .add("phone", phone)
                .add("deviceId", deviceId)
                .build();
        var smsResponse = api.requestSms(sessionId, dataForSmsRequest).execute();
        var operationTicket = smsResponse.body().getOperationTicket();

        if (operationTicket == null) {
            logger.error("SMS code doesn't sent! \n" + smsResponse.message());
            throw new RuntimeException("SMS code doesn't sent!");
        }
        logger.debug("SMS code have been sent, received operationTicket: " + operationTicket);
        return operationTicket;
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
    }

}
