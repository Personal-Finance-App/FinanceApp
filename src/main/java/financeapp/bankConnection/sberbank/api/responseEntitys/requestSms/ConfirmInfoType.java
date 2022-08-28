
package financeapp.bankConnection.sberbank.api.responseEntitys.requestSms;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for confirmInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confirmInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="smsp" type="{}smspType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirmInfoType", propOrder = {
    "type",
    "smsp"
})
public class ConfirmInfoType {

    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected SmspType smsp;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the smsp property.
     * 
     * @return
     *     possible object is
     *     {@link SmspType }
     *     
     */
    public SmspType getSmsp() {
        return smsp;
    }

    /**
     * Sets the value of the smsp property.
     * 
     * @param value
     *     allowed object is
     *     {@link SmspType }
     *     
     */
    public void setSmsp(SmspType value) {
        this.smsp = value;
    }

}
