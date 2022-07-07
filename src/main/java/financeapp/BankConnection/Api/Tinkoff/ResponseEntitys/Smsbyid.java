package financeapp.BankConnection.Api.Tinkoff.ResponseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
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

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(String confirmationType) {
        this.confirmationType = confirmationType;
    }

}
