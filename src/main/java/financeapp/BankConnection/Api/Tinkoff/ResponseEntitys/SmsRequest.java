package financeapp.BankConnection.Api.Tinkoff.ResponseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class SmsRequest {

    @SerializedName("confirmationData")
    @Expose
    private ConfirmationData confirmationData;
    @SerializedName("confirmations")
    @Expose
    private List<String> confirmations = null;
    @SerializedName("initialOperation")
    @Expose
    private String initialOperation;
    @SerializedName("operationTicket")
    @Expose
    private String operationTicket;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;

    public ConfirmationData getConfirmationData() {
        return confirmationData;
    }

    public void setConfirmationData(ConfirmationData confirmationData) {
        this.confirmationData = confirmationData;
    }

    public List<String> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(List<String> confirmations) {
        this.confirmations = confirmations;
    }

    public String getInitialOperation() {
        return initialOperation;
    }

    public void setInitialOperation(String initialOperation) {
        this.initialOperation = initialOperation;
    }

    public String getOperationTicket() {
        return operationTicket;
    }

    public void setOperationTicket(String operationTicket) {
        this.operationTicket = operationTicket;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

}
