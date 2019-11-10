package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderBookDomain.
 */
@Entity
@Table(name = "order_book_domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderBookDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "order_ammount", precision=10, scale=2)
    private BigDecimal orderAmmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "ccv")
    private String ccv;

    @Column(name = "ship_to")
    private String shipTo;

    @OneToMany(mappedBy = "orderBookDomain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchasedBook> purchasedBooks = new HashSet<>();

    @ManyToOne
    private Buyer buyer;

    @ManyToOne
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public OrderBookDomain startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OrderBookDomain endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getOrderAmmount() {
        return orderAmmount;
    }

    public OrderBookDomain orderAmmount(BigDecimal orderAmmount) {
        this.orderAmmount = orderAmmount;
        return this;
    }

    public void setOrderAmmount(BigDecimal orderAmmount) {
        this.orderAmmount = orderAmmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public OrderBookDomain orderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public OrderBookDomain nameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
        return this;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public OrderBookDomain creditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public OrderBookDomain expiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCcv() {
        return ccv;
    }

    public OrderBookDomain ccv(String ccv) {
        this.ccv = ccv;
        return this;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getShipTo() {
        return shipTo;
    }

    public OrderBookDomain shipTo(String shipTo) {
        this.shipTo = shipTo;
        return this;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Set<PurchasedBook> getPurchasedBooks() {
        return purchasedBooks;
    }

    public OrderBookDomain purchasedBooks(Set<PurchasedBook> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
        return this;
    }

    public OrderBookDomain addPurchasedBook(PurchasedBook purchasedBook) {
        this.purchasedBooks.add(purchasedBook);
        purchasedBook.setOrderBookDomain(this);
        return this;
    }

    public OrderBookDomain removePurchasedBook(PurchasedBook purchasedBook) {
        this.purchasedBooks.remove(purchasedBook);
        purchasedBook.setOrderBookDomain(null);
        return this;
    }

    public void setPurchasedBooks(Set<PurchasedBook> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public OrderBookDomain buyer(Buyer buyer) {
        this.buyer = buyer;
        return this;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Store getStore() {
        return store;
    }

    public OrderBookDomain store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
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
        OrderBookDomain orderBookDomain = (OrderBookDomain) o;
        if (orderBookDomain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderBookDomain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderBookDomain{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", orderAmmount=" + getOrderAmmount() +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", nameOnCard='" + getNameOnCard() + "'" +
            ", creditCard='" + getCreditCard() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", ccv='" + getCcv() + "'" +
            ", shipTo='" + getShipTo() + "'" +
            "}";
    }
}
