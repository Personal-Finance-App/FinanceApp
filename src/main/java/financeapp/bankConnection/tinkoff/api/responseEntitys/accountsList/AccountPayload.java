
package financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AccountPayload {

    @SerializedName("externalAccountNumber")
    @Expose
    private String externalAccountNumber;
    @SerializedName("accountGroup")
    @Expose
    private String accountGroup;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;


}
