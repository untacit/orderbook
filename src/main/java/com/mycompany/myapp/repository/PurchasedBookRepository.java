package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PurchasedBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PurchasedBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasedBookRepository extends JpaRepository<PurchasedBook, Long> {

}
