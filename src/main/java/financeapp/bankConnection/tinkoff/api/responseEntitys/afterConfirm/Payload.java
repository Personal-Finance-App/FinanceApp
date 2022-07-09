
package financeapp.bankConnection.tinkoff.api.responseEntitys.afterConfirm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Payload {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("accessLevel")
    @Expose
    private String accessLevel;
    @SerializedName("hasPassword")
    @Expose
    private Boolean hasPassword;
    @SerializedName("noClient")
    @Expose
    private Boolean noClient;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("ssoId")
    @Expose
    private String ssoId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Boolean getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(Boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public Boolean getNoClient() {
        return noClient;
    }

    public void setNoClient(Boolean noClient) {
        this.noClient = noClient;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

}
