package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OrderBookDomain;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the OrderBookDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderBookDomainRepository extends JpaRepository<OrderBookDomain, Long> {
    @Query("select distinct order_book_domain from OrderBookDomain order_book_domain left join fetch order_book_domain.books left join fetch order_book_domain.buyers left join fetch order_book_domain.stores")
    List<OrderBookDomain> findAllWithEagerRelationships();

    @Query("select order_book_domain from OrderBookDomain order_book_domain left join fetch order_book_domain.books left join fetch order_book_domain.buyers left join fetch order_book_domain.stores where order_book_domain.id =:id")
    OrderBookDomain findOneWithEagerRelationships(@Param("id") Long id);

}
