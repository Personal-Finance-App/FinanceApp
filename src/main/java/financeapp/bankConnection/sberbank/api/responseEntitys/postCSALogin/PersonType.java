
package financeapp.bankConnection.sberbank.api.responseEntitys.postCSALogin;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for personType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="surName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="patrName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CollectBioAgreement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personType", propOrder = {
    "surName",
    "firstName",
    "patrName",
    "collectBioAgreement"
})
public class PersonType {

    @XmlElement(required = true)
    protected String surName;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String patrName;
    @XmlElement(name = "CollectBioAgreement", required = true)
    protected String collectBioAgreement;

    /**
     * Gets the value of the surName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurName() {
        return surName;
    }

    /**
     * Sets the value of the surName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurName(String value) {
        this.surName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the patrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatrName() {
        return patrName;
    }

    /**
     * Sets the value of the patrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatrName(String value) {
        this.patrName = value;
    }

    /**
     * Gets the value of the collectBioAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectBioAgreement() {
        return collectBioAgreement;
    }

    /**
     * Sets the value of the collectBioAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectBioAgreement(String value) {
        this.collectBioAgreement = value;
    }

}
