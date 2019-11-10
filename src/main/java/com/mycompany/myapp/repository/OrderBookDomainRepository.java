package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OrderBookDomain;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderBookDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderBookDomainRepository extends JpaRepository<OrderBookDomain, Long> {

}
