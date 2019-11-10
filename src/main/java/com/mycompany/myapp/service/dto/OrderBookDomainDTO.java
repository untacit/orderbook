package com.mycompany.myapp.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrderBookDomain entity.
 */
public class OrderBookDomainDTO implements Serializable {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal orderAmmount;

    private String orderStatus;

    private String nameOnCard;

    private String creditCard;

    private String expiryDate;

    private String ccv;

    private String shipTo;

    private Long buyerId;

    private String buyerName;

    private Long storeId;

    private String storeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getOrderAmmount() {
        return orderAmmount;
    }

    public void setOrderAmmount(BigDecimal orderAmmount) {
        this.orderAmmount = orderAmmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderBookDomainDTO orderBookDomainDTO = (OrderBookDomainDTO) o;
        if(orderBookDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderBookDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderBookDomainDTO{" +
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
