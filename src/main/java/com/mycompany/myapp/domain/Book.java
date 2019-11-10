package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "pub_year")
    private String pubYear;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderBookDomain> orderBookDomains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Book name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubYear() {
        return pubYear;
    }

    public Book pubYear(String pubYear) {
        this.pubYear = pubYear;
        return this;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Book price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<OrderBookDomain> getOrderBookDomains() {
        return orderBookDomains;
    }

    public Book orderBookDomains(Set<OrderBookDomain> orderBookDomains) {
        this.orderBookDomains = orderBookDomains;
        return this;
    }

    public Book addOrderBookDomain(OrderBookDomain orderBookDomain) {
        this.orderBookDomains.add(orderBookDomain);
        orderBookDomain.getBooks().add(this);
        return this;
    }

    public Book removeOrderBookDomain(OrderBookDomain orderBookDomain) {
        this.orderBookDomains.remove(orderBookDomain);
        orderBookDomain.getBooks().remove(this);
        return this;
    }

    public void setOrderBookDomains(Set<OrderBookDomain> orderBookDomains) {
        this.orderBookDomains = orderBookDomains;
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
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", pubYear='" + getPubYear() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
