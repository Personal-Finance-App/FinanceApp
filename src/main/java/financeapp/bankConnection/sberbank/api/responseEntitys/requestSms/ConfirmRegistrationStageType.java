
package financeapp.bankConnection.sberbank.api.responseEntitys.requestSms;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for confirmRegistrationStageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confirmRegistrationStageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mGUID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirmRegistrationStageType", propOrder = {
    "mguid"
})
public class ConfirmRegistrationStageType {

    @XmlElement(name = "mGUID", required = true)
    protected String mguid;

    /**
     * Gets the value of the mguid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMGUID() {
        return mguid;
    }

    /**
     * Sets the value of the mguid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMGUID(String value) {
        this.mguid = value;
    }

}
