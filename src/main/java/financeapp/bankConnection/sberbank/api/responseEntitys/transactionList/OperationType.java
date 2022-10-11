
package financeapp.bankConnection.sberbank.api.responseEntitys.transactionList;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for operationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="to" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Перевод по СБП"/&gt;
 *               &lt;enumeration value="Оплата товаров и услуг"/&gt;
 *               &lt;enumeration value="Внесение наличных"/&gt;
 *               &lt;enumeration value="Входящий перевод"/&gt;
 *               &lt;enumeration value="Комиссии"/&gt;
 *               &lt;enumeration value="Прочие списания"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="operationAmount" type="{}operationAmountType"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="form"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="P2PSBPInTransfer"/&gt;
 *               &lt;enumeration value="ExtCardPayment"/&gt;
 *               &lt;enumeration value="ExtCardCashIn"/&gt;
 *               &lt;enumeration value="ExtCardTransferIn"/&gt;
 *               &lt;enumeration value="TakingMeans"/&gt;
 *               &lt;enumeration value="ExtCardOtherOut"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="nationalAmount" type="{}nationalAmountType"/&gt;
 *         &lt;element name="classificationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operationType", propOrder = {
    "state",
    "date",
    "to",
    "description",
    "operationAmount",
    "type",
    "form",
    "nationalAmount",
    "classificationCode"
})
public class OperationType {

    @XmlElement(required = true)
    protected String state;
    @XmlElement(required = true)
    protected String date;
    @XmlElement(required = true)
    protected String to;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected OperationAmountType operationAmount;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String form;
    @XmlElement(required = true)
    protected NationalAmountType nationalAmount;
    @XmlElement(required = true)
    protected String classificationCode;

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTo(String value) {
        this.to = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the operationAmount property.
     * 
     * @return
     *     possible object is
     *     {@link OperationAmountType }
     *     
     */
    public OperationAmountType getOperationAmount() {
        return operationAmount;
    }

    /**
     * Sets the value of the operationAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationAmountType }
     *     
     */
    public void setOperationAmount(OperationAmountType value) {
        this.operationAmount = value;
    }

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
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForm(String value) {
        this.form = value;
    }

    /**
     * Gets the value of the nationalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link NationalAmountType }
     *     
     */
    public NationalAmountType getNationalAmount() {
        return nationalAmount;
    }

    /**
     * Sets the value of the nationalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link NationalAmountType }
     *     
     */
    public void setNationalAmount(NationalAmountType value) {
        this.nationalAmount = value;
    }

    /**
     * Gets the value of the classificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificationCode() {
        return classificationCode;
    }

    /**
     * Sets the value of the classificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificationCode(String value) {
        this.classificationCode = value;
    }

}
