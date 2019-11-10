package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OrderBookProcess;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderBookProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderBookProcessRepository extends JpaRepository<OrderBookProcess, Long> {

    OrderBookProcess findByCamundaProcessInstanceId(String processInstanceId);
    

}
