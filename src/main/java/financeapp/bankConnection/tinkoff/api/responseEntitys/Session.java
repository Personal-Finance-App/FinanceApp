package financeapp.bankConnection.tinkoff.api.responseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Setter
@Getter
public class Session {

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;


}
