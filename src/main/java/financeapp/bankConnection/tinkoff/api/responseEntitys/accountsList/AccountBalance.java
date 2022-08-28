package financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Getter
public class AccountBalance {
    @SerializedName("value")
    @Expose
    private Double value;
}
