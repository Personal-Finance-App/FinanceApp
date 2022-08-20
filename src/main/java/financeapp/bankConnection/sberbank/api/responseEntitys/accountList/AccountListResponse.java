
package financeapp.bankConnection.sberbank.api.responseEntitys.accountList;

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
 *         &lt;element name="cards" type="{}cardsType"/&gt;
 *         &lt;element name="accounts" type="{}accountsType"/&gt;
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
    "cards",
    "accounts"
})
@XmlRootElement(name = "response")
public class AccountListResponse {

    @XmlElement(required = true)
    protected StatusType status;
    @XmlElement(required = true)
    protected CardsType cards;
    @XmlElement(required = true)
    protected AccountsType accounts;

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
     * Gets the value of the cards property.
     * 
     * @return
     *     possible object is
     *     {@link CardsType }
     *     
     */
    public CardsType getCards() {
        return cards;
    }

    /**
     * Sets the value of the cards property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardsType }
     *     
     */
    public void setCards(CardsType value) {
        this.cards = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link AccountsType }
     *     
     */
    public AccountsType getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountsType }
     *     
     */
    public void setAccounts(AccountsType value) {
        this.accounts = value;
    }

}
