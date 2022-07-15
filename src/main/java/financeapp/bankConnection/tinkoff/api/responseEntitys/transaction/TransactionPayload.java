package financeapp.bankConnection.tinkoff.api.responseEntitys.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;


@Generated("jsonschema2pojo")
@Getter
@Setter
public class TransactionPayload {
    @SerializedName("amount")
    @Expose
    private AmountPayload amountPayload;

    class AmountPayload {
        @SerializedName("value")
        @Expose
        private double amount;
    }

    @SerializedName("description")
    private String description = null;
    @SerializedName("operationTime")
    @Expose
    private DateTimePayload dateTime;

    class DateTimePayload {
        @SerializedName("milliseconds")
        @Expose
        private String dateTimeMilliSeconds;
    }


    @SerializedName("category")
    @Expose
    private CategoryNamePayload categoryNamePayload;

    class CategoryNamePayload {
        @SerializedName("name")
        @Expose
        private String categoryName;
    }

    @SerializedName("merchant")
    @Expose
    private MerchantNamePayload merchantNamePayload;

    class MerchantNamePayload {
        @SerializedName("name")
        @Expose
        private String merchantName;
    }

    @SerializedName("mccString")
    @Expose
    private String mccString;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("senderDetails")
    private String senderDetails = null;
    @SerializedName("message")
    private String message = null;

    @SerializedName("status")
    private String status;

    public String getDateTimeMilliSeconds() {
        return this.dateTime.dateTimeMilliSeconds;
    }

    public String getCategoryName() {
        return this.categoryNamePayload.categoryName;
    }

    public String getMerchantName() {
        if (this.merchantNamePayload != null)
            return this.merchantNamePayload.merchantName;
        return null;
    }

    public double getAmount() {
        return this.amountPayload.amount;
    }
}

