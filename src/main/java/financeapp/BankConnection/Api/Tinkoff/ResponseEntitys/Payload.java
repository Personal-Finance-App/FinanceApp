
package financeapp.BankConnection.Api.Tinkoff.ResponseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Payload {

    @SerializedName("sessionid")
    @Expose
    private String sessionid;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

}
