
package financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class AccountsAnswer {

    @SerializedName("payload")
    @Expose
    private List<AccountPayload> payload = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;

    public List<AccountPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<AccountPayload> payload) {
        this.payload = payload;
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
