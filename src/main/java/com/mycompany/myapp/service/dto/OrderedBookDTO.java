package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrderedBook entity.
 */
public class OrderedBookDTO implements Serializable {

    private Long id;

    private BigDecimal price;

    private Long bookId;

    private Long orderBookDomainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getOrderBookDomainId() {
        return orderBookDomainId;
    }

    public void setOrderBookDomainId(Long orderBookDomainId) {
        this.orderBookDomainId = orderBookDomainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderedBookDTO orderedBookDTO = (OrderedBookDTO) o;
        if(orderedBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderedBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderedBookDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
