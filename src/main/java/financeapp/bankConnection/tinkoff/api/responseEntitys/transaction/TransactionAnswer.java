package financeapp.bankConnection.tinkoff.api.responseEntitys.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
@Getter
@Setter
public class TransactionAnswer {

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("payload")
    @Expose
    private List<TransactionPayload> payload;
    @SerializedName("trackingId")
    @Expose
    private String trackingId;


}