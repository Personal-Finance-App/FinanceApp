package financeapp.bankConnection.tinkoff.api.responseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
@Setter
@Getter
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

}
