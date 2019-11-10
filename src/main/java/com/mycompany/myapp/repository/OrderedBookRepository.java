package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OrderedBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderedBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderedBookRepository extends JpaRepository<OrderedBook, Long> {

}
