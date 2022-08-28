
package financeapp.bankConnection.sberbank.api.responseEntitys.setUpPin;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for responseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="status" type="{}statusType"/&gt;
 *         &lt;element name="loginCompleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="loginData" type="{}loginDataType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseType", propOrder = {
    "status",
    "loginCompleted",
    "loginData"
})
@XmlRootElement(name = "response")
public class SetUpPinResponse {

    @XmlElement(required = true)
    protected StatusType status;
    protected boolean loginCompleted;
    @XmlElement(required = true)
    protected LoginDataType loginData;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the loginCompleted property.
     * 
     */
    public boolean isLoginCompleted() {
        return loginCompleted;
    }

    /**
     * Sets the value of the loginCompleted property.
     * 
     */
    public void setLoginCompleted(boolean value) {
        this.loginCompleted = value;
    }

    /**
     * Gets the value of the loginData property.
     * 
     * @return
     *     possible object is
     *     {@link LoginDataType }
     *     
     */
    public LoginDataType getLoginData() {
        return loginData;
    }

    /**
     * Sets the value of the loginData property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginDataType }
     *     
     */
    public void setLoginData(LoginDataType value) {
        this.loginData = value;
    }

}
