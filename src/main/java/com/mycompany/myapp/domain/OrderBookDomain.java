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

    @Column(name = "ship_to")
    private String shipTo;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentDetails paymentDetails;

    @OneToMany(mappedBy = "orderBookDomain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchasedBook> purchasedBooks = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "order_book_domain_book",
               joinColumns = @JoinColumn(name="order_book_domains_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="books_id", referencedColumnName="id"))
    private Set<Book> books = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "order_book_domain_buyer",
               joinColumns = @JoinColumn(name="order_book_domains_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="buyers_id", referencedColumnName="id"))
    private Set<Buyer> buyers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "order_book_domain_store",
               joinColumns = @JoinColumn(name="order_book_domains_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="stores_id", referencedColumnName="id"))
    private Set<Store> stores = new HashSet<>();

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

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public OrderBookDomain paymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
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

    public Set<Book> getBooks() {
        return books;
    }

    public OrderBookDomain books(Set<Book> books) {
        this.books = books;
        return this;
    }

    public OrderBookDomain addBook(Book book) {
        this.books.add(book);
        book.getOrderBookDomains().add(this);
        return this;
    }

    public OrderBookDomain removeBook(Book book) {
        this.books.remove(book);
        book.getOrderBookDomains().remove(this);
        return this;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Buyer> getBuyers() {
        return buyers;
    }

    public OrderBookDomain buyers(Set<Buyer> buyers) {
        this.buyers = buyers;
        return this;
    }

    public OrderBookDomain addBuyer(Buyer buyer) {
        this.buyers.add(buyer);
        buyer.getOrderBookDomains().add(this);
        return this;
    }

    public OrderBookDomain removeBuyer(Buyer buyer) {
        this.buyers.remove(buyer);
        buyer.getOrderBookDomains().remove(this);
        return this;
    }

    public void setBuyers(Set<Buyer> buyers) {
        this.buyers = buyers;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public OrderBookDomain stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public OrderBookDomain addStore(Store store) {
        this.stores.add(store);
        store.getOrderBookDomains().add(this);
        return this;
    }

    public OrderBookDomain removeStore(Store store) {
        this.stores.remove(store);
        store.getOrderBookDomains().remove(this);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
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
            ", shipTo='" + getShipTo() + "'" +
            "}";
    }
}
