
package financeapp.bankConnection.tinkoff.api.responseEntitys.pingConfirm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Payload {

    @SerializedName("accessLevel")
    @Expose
    private String accessLevel;
    @SerializedName("unreadMessagesCount")
    @Expose
    private Integer unreadMessagesCount;
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
