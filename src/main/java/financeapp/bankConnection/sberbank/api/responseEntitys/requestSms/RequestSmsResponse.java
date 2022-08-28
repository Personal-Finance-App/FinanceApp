package financeapp.bankConnection.sberbank.api.responseEntitys.requestSms;


import financeapp.bankConnection.sberbank.api.responseEntitys.setUpPin.StatusType;

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
 *         &lt;element name="confirmRegistrationStage" type="{}confirmRegistrationStageType"/&gt;
 *         &lt;element name="confirmInfo" type="{}confirmInfoType"/&gt;
 *         &lt;element name="registrationParameters" type="{}registrationParametersType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestSmsResponse {

    @XmlElement(required = true)
    protected StatusType status;
    protected boolean loginCompleted;
    @XmlElement(required = true)
    protected ConfirmRegistrationStageType confirmRegistrationStage;
    @XmlElement(required = true)
    protected ConfirmInfoType confirmInfo;
    @XmlElement(required = true)
    protected RegistrationParametersType registrationParameters;

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
     * Gets the value of the confirmRegistrationStage property.
     *
     * @return
     *     possible object is
     *     {@link ConfirmRegistrationStageType }
     *
     */
    public ConfirmRegistrationStageType getConfirmRegistrationStage() {
        return confirmRegistrationStage;
    }

    /**
     * Sets the value of the confirmRegistrationStage property.
     *
     * @param value
     *     allowed object is
     *     {@link ConfirmRegistrationStageType }
     *
     */
    public void setConfirmRegistrationStage(ConfirmRegistrationStageType value) {
        this.confirmRegistrationStage = value;
    }

    /**
     * Gets the value of the confirmInfo property.
     *
     * @return
     *     possible object is
     *     {@link ConfirmInfoType }
     *
     */
    public ConfirmInfoType getConfirmInfo() {
        return confirmInfo;
    }

    /**
     * Sets the value of the confirmInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link ConfirmInfoType }
     *
     */
    public void setConfirmInfo(ConfirmInfoType value) {
        this.confirmInfo = value;
    }

    /**
     * Gets the value of the registrationParameters property.
     *
     * @return
     *     possible object is
     *     {@link RegistrationParametersType }
     *
     */
    public RegistrationParametersType getRegistrationParameters() {
        return registrationParameters;
    }

    /**
     * Sets the value of the registrationParameters property.
     *
     * @param value
     *     allowed object is
     *     {@link RegistrationParametersType }
     *
     */
    public void setRegistrationParameters(RegistrationParametersType value) {
        this.registrationParameters = value;
    }

}
