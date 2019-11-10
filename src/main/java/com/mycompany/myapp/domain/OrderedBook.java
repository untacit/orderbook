package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A OrderedBook.
 */
@Entity
@Table(name = "ordered_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderedBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @OneToOne
    @JoinColumn(unique = true)
    private Book book;

    @ManyToOne
    private OrderBookDomain orderBookDomain;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderedBook price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public OrderedBook book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public OrderBookDomain getOrderBookDomain() {
        return orderBookDomain;
    }

    public OrderedBook orderBookDomain(OrderBookDomain orderBookDomain) {
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
        OrderedBook orderedBook = (OrderedBook) o;
        if (orderedBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderedBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderedBook{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
