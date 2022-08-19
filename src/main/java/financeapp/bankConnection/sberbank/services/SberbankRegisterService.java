package financeapp.bankConnection.sberbank.services;

import financeapp.accounts.repositories.AccountRepo;
import financeapp.bankConnection.sberbank.SberbankConnection;
import financeapp.bankConnection.sberbank.SberbankConnectionRepo;
import financeapp.bankConnection.sberbank.api.calls.SberbankApi;
import financeapp.bankConnection.sberbank.api.calls.SberbankApiFactory;
import financeapp.bankConnection.tinkoff.TinkoffConnectionRepo;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApi;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApiFactory;
import financeapp.bankConnection.tinkoff.services.TinkoffService;
import financeapp.category.CategoryService;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SberbankRegisterService {
    public SberbankRegisterService(SberbankConnectionRepo sberbankConnectionRepo, AccountRepo accountRepo, TransactionService transactionService, CategoryService categoryService) {
        this.sberbankConnectionRepo = sberbankConnectionRepo;
        this.accountRepo = accountRepo;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    private final SberbankConnectionRepo sberbankConnectionRepo;
    private final AccountRepo accountRepo;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    SberbankApi api = SberbankApiFactory.getService();

    public String requestSms(String login) throws IOException {
        var result = api.requestSMS("register", login).execute();
        assert result.body() != null;
        if (result.body().getStatus().getCode() == 0)
            return result.body().getConfirmRegistrationStage().getMGUID();
        throw new RuntimeException("There are error occur");
    }

    public boolean registerFinal(String mGUID, CustomUser user, String smsCode, String pinCode) throws IOException {
        if (!confirmSms(mGUID, smsCode))
            return false;
        return setUpPin(user, mGUID, pinCode);
    }

    private boolean confirmSms(String mGUID, String sms) throws IOException {
        var result = api.confirmSms("confirm", mGUID, sms, sms).execute();
        assert result.body() != null;
        return result.body().getStatus().getCode() == 0;
    }

    private boolean setUpPin(CustomUser user, String mGUID, String pinCode) throws IOException {
        var connection = sberbankConnectionRepo.getSberbankConnectionByUser(user);
        if (connection == null) {
            connection = new SberbankConnection();
            connection.setUser(user);
        }
        var result = api.setUpPing("createPIN", mGUID, pinCode).execute();
        assert result.body() != null;
        if (result.body().getStatus().getCode() == 0) {
            connection.setPinCode(pinCode);
            connection.setMGUID(mGUID);
            sberbankConnectionRepo.save(connection);
            return true;
        }

        return false;
    }
}
