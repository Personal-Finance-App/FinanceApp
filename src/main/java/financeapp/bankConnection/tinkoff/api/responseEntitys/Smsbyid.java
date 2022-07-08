package financeapp.bankConnection.tinkoff.api.responseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Setter
@Getter
public class Smsbyid {

    @SerializedName("codeLength")
    @Expose
    private Integer codeLength;
    @SerializedName("codeType")
    @Expose
    private String codeType;
    @SerializedName("confirmationType")
    @Expose
    private String confirmationType;

}
