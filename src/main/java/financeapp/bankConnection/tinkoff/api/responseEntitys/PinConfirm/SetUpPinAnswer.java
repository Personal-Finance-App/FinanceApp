package financeapp.bankConnection.tinkoff.api.responseEntitys.PinConfirm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class SetUpPinAnswer {

    @SerializedName("payload")
    @Expose
    private Payload payload;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
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
