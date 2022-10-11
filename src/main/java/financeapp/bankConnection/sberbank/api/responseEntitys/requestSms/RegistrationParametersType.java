
package financeapp.bankConnection.sberbank.api.responseEntitys.requestSms;

import java.math.BigInteger;
import javax.xml.bind.annotation.*;

/**
 * <p>Java class for registrationParametersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrationParametersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="minimumPINLength" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrationParametersType", propOrder = {
    "minimumPINLength"
})
public class RegistrationParametersType {

    @XmlElement(required = true)
    protected BigInteger minimumPINLength;

    /**
     * Gets the value of the minimumPINLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinimumPINLength() {
        return minimumPINLength;
    }

    /**
     * Sets the value of the minimumPINLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinimumPINLength(BigInteger value) {
        this.minimumPINLength = value;
    }

}
