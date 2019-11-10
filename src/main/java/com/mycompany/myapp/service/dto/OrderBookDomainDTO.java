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

    private String shipTo;

    private Long paymentDetailsId;

    private Set<BookDTO> books = new HashSet<>();

    private Set<BuyerDTO> buyers = new HashSet<>();

    private Set<StoreDTO> stores = new HashSet<>();

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

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Long getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setPaymentDetailsId(Long paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDTO> books) {
        this.books = books;
    }

    public Set<BuyerDTO> getBuyers() {
        return buyers;
    }

    public void setBuyers(Set<BuyerDTO> buyers) {
        this.buyers = buyers;
    }

    public Set<StoreDTO> getStores() {
        return stores;
    }

    public void setStores(Set<StoreDTO> stores) {
        this.stores = stores;
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
            ", shipTo='" + getShipTo() + "'" +
            "}";
    }
}
