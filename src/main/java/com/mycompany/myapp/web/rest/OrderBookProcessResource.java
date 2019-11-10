package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.annotation.UntacitCancelProcessInstance;
import com.mycompany.myapp.annotation.UntacitCreateProcessInstance;
import com.mycompany.myapp.service.OrderBookProcessService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
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
 * REST controller for managing OrderBookProcess.
 */
@RestController
@RequestMapping("/api")
public class OrderBookProcessResource {

    private final Logger log = LoggerFactory.getLogger(OrderBookProcessResource.class);

    private static final String ENTITY_NAME = "orderBookProcess";

    private final OrderBookProcessService orderBookProcessService;

    public OrderBookProcessResource(OrderBookProcessService orderBookProcessService) {
        this.orderBookProcessService = orderBookProcessService;
    }

    /**
     * POST  /order-book-processes : Create a new orderBookProcess.
     *
     * @param orderBookProcessDTO the orderBookProcessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderBookProcessDTO, or with status 400 (Bad Request) if the orderBookProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-book-processes")
    @Timed
    @UntacitCreateProcessInstance("procidOrderBook")
    public ResponseEntity<OrderBookProcessDTO> createOrderBookProcess(@RequestBody OrderBookProcessDTO orderBookProcessDTO) throws URISyntaxException {
        log.debug("REST request to save OrderBookProcess : {}", orderBookProcessDTO);
        if (orderBookProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderBookProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderBookProcessDTO result = orderBookProcessService.save(orderBookProcessDTO);
        return ResponseEntity.created(new URI("/api/order-book-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-book-processes : Updates an existing orderBookProcess.
     *
     * @param orderBookProcessDTO the orderBookProcessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderBookProcessDTO,
     * or with status 400 (Bad Request) if the orderBookProcessDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderBookProcessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-book-processes")
    @Timed
    public ResponseEntity<OrderBookProcessDTO> updateOrderBookProcess(@RequestBody OrderBookProcessDTO orderBookProcessDTO) throws URISyntaxException {
        log.debug("REST request to update OrderBookProcess : {}", orderBookProcessDTO);
        if (orderBookProcessDTO.getId() == null) {
            return createOrderBookProcess(orderBookProcessDTO);
        }
        OrderBookProcessDTO result = orderBookProcessService.save(orderBookProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderBookProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-book-processes : get all the orderBookProcesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderBookProcesses in body
     */
    @GetMapping("/order-book-processes")
    @Timed
    public List<OrderBookProcessDTO> getAllOrderBookProcesses() {
        log.debug("REST request to get all OrderBookProcesses");
        return orderBookProcessService.findAll();
        }

    /**
     * GET  /order-book-processes/:id : get the "id" orderBookProcess.
     *
     * @param id the id of the orderBookProcessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderBookProcessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-book-processes/{id}")
    @Timed
    public ResponseEntity<OrderBookProcessDTO> getOrderBookProcess(@PathVariable Long id) {
        log.debug("REST request to get OrderBookProcess : {}", id);
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderBookProcessDTO));
    }

    /**
     * DELETE  /order-book-processes/:id : delete the "id" orderBookProcess.
     *
     * @param id the id of the orderBookProcessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-book-processes/{id}")
    @Timed
    @UntacitCancelProcessInstance("procidOrderBook")
    public ResponseEntity<Void> deleteOrderBookProcess(@PathVariable Long id) {
        log.debug("REST request to delete OrderBookProcess : {}", id);
        orderBookProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
