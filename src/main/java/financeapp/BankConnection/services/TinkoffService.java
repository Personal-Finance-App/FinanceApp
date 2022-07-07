package financeapp.BankConnection.services;

import financeapp.BankConnection.Api.Tinkoff.TinkoffApi;
import financeapp.BankConnection.Api.Tinkoff.TinkoffApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TinkoffService {

    TinkoffApi api = TinkoffApiFactory.getService();
    String sessionId = null;
    Logger logger = LoggerFactory.getLogger(TinkoffService.class);

    public void RegisterSendSms(String phone) throws IOException {
        String deviceId = "34567890-7654dfvghj"; //TODO: доставать

        logger.debug("Request sessionId");
        sessionId = api.getSession(deviceId).execute().body().getPayload().getSessionid();
        logger.debug("Received SessionId: " + sessionId);

        if (sessionId == null)
            throw new RuntimeException("Can't get sessionId");

        var smsResponse = api.requestSms(phone, sessionId, deviceId).execute();
        var operationTicket = smsResponse.body().getOperationTicket();

        if (operationTicket == null) {
            logger.error("SMS code doesn't sent! \n" + smsResponse.message());
            throw new RuntimeException("SMS code doesn't sent!");
        }
        logger.debug("SMS code have been sent, received operationTicket: " + operationTicket);
    }

    public void RegisterFinal(String sessionId, String operationTicket, String sms, String password) {
        String deviceId = "34567890-7654dfvghj"; //TODO: доставать

        String smsConfirm = "{\"SMSBYID\":\" " + sms + "\"}";
        logger.debug("For session " + sessionId + " and operation ticket " + operationTicket + "sending confirmation code");
        logger.debug("Confirmation Data " + smsConfirm);
        var response = api.confirmSms(sessionId, operationTicket, "auth/by/phone", smsConfirm, deviceId);
    }

}
