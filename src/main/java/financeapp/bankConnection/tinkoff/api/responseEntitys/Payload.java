package financeapp.bankConnection.tinkoff.api.responseEntitys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Setter
@Getter
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
