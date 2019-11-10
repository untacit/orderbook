package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PurchasedBook entity.
 */
public class PurchasedBookDTO implements Serializable {

    private Long id;

    @Min(value = 1)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    private Integer price;

    private Long orderBookDomainId;

    private Long bookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getOrderBookDomainId() {
        return orderBookDomainId;
    }

    public void setOrderBookDomainId(Long orderBookDomainId) {
        this.orderBookDomainId = orderBookDomainId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchasedBookDTO purchasedBookDTO = (PurchasedBookDTO) o;
        if(purchasedBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchasedBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchasedBookDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            "}";
    }
}
