
package financeapp.bankConnection.sberbank.api.responseEntitys.requestSms;

import java.math.BigInteger;
import javax.xml.bind.annotation.*;

/**
 * <p>Java class for smspType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="smspType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lifeTime" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="attemptsRemain" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "smspType", propOrder = {
    "lifeTime",
    "attemptsRemain"
})
public class SmspType {

    @XmlElement(required = true)
    protected BigInteger lifeTime;
    @XmlElement(required = true)
    protected BigInteger attemptsRemain;

    /**
     * Gets the value of the lifeTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLifeTime() {
        return lifeTime;
    }

    /**
     * Sets the value of the lifeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLifeTime(BigInteger value) {
        this.lifeTime = value;
    }

    /**
     * Gets the value of the attemptsRemain property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAttemptsRemain() {
        return attemptsRemain;
    }

    /**
     * Sets the value of the attemptsRemain property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAttemptsRemain(BigInteger value) {
        this.attemptsRemain = value;
    }

}
