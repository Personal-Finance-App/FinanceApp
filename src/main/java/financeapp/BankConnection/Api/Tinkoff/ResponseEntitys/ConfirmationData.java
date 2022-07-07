package financeapp.BankConnection.Api.Tinkoff.ResponseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ConfirmationData {

    @SerializedName("SMSBYID")
    @Expose
    private Smsbyid smsbyid;

    public Smsbyid getSmsbyid() {
        return smsbyid;
    }

    public void setSmsbyid(Smsbyid smsbyid) {
        this.smsbyid = smsbyid;
    }

}
