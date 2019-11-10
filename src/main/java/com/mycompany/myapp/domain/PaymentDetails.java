package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentDetails.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "ccv")
    private String ccv;

    @OneToOne(mappedBy = "paymentDetails")
    @JsonIgnore
    private OrderBookDomain orderBookDomain;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public PaymentDetails nameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
        return this;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public PaymentDetails creditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public PaymentDetails expiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCcv() {
        return ccv;
    }

    public PaymentDetails ccv(String ccv) {
        this.ccv = ccv;
        return this;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public OrderBookDomain getOrderBookDomain() {
        return orderBookDomain;
    }

    public PaymentDetails orderBookDomain(OrderBookDomain orderBookDomain) {
        this.orderBookDomain = orderBookDomain;
        return this;
    }

    public void setOrderBookDomain(OrderBookDomain orderBookDomain) {
        this.orderBookDomain = orderBookDomain;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentDetails paymentDetails = (PaymentDetails) o;
        if (paymentDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
            "id=" + getId() +
            ", nameOnCard='" + getNameOnCard() + "'" +
            ", creditCard='" + getCreditCard() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", ccv='" + getCcv() + "'" +
            "}";
    }
}
