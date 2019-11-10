package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.OrderBookDomainService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrderBookDomainDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderBookDomain.
 */
@RestController
@RequestMapping("/api")
public class OrderBookDomainResource {

    private final Logger log = LoggerFactory.getLogger(OrderBookDomainResource.class);

    private static final String ENTITY_NAME = "orderBookDomain";

    private final OrderBookDomainService orderBookDomainService;

    public OrderBookDomainResource(OrderBookDomainService orderBookDomainService) {
        this.orderBookDomainService = orderBookDomainService;
    }


    /**
     * GET  /order-book-domains : get all the orderBookDomains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderBookDomains in body
     */
    @GetMapping("/order-book-domains")
    @Timed
    public List<OrderBookDomainDTO> getAllOrderBookDomains() {
        log.debug("REST request to get all OrderBookDomains");
        return orderBookDomainService.findAll();
        }

    /**
     * GET  /order-book-domains/:id : get the "id" orderBookDomain.
     *
     * @param id the id of the orderBookDomainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderBookDomainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-book-domains/{id}")
    @Timed
    public ResponseEntity<OrderBookDomainDTO> getOrderBookDomain(@PathVariable Long id) {
        log.debug("REST request to get OrderBookDomain : {}", id);
        OrderBookDomainDTO orderBookDomainDTO = orderBookDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderBookDomainDTO));
    }
}
