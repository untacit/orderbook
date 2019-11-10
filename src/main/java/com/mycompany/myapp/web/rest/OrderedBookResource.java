package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OrderedBook;

import com.mycompany.myapp.repository.OrderedBookRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrderedBookDTO;
import com.mycompany.myapp.service.mapper.OrderedBookMapper;
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
 * REST controller for managing OrderedBook.
 */
@RestController
@RequestMapping("/api")
public class OrderedBookResource {

    private final Logger log = LoggerFactory.getLogger(OrderedBookResource.class);

    private static final String ENTITY_NAME = "orderedBook";

    private final OrderedBookRepository orderedBookRepository;

    private final OrderedBookMapper orderedBookMapper;

    public OrderedBookResource(OrderedBookRepository orderedBookRepository, OrderedBookMapper orderedBookMapper) {
        this.orderedBookRepository = orderedBookRepository;
        this.orderedBookMapper = orderedBookMapper;
    }

    /**
     * POST  /ordered-books : Create a new orderedBook.
     *
     * @param orderedBookDTO the orderedBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderedBookDTO, or with status 400 (Bad Request) if the orderedBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordered-books")
    @Timed
    public ResponseEntity<OrderedBookDTO> createOrderedBook(@RequestBody OrderedBookDTO orderedBookDTO) throws URISyntaxException {
        log.debug("REST request to save OrderedBook : {}", orderedBookDTO);
        if (orderedBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderedBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderedBook orderedBook = orderedBookMapper.toEntity(orderedBookDTO);
        orderedBook = orderedBookRepository.save(orderedBook);
        OrderedBookDTO result = orderedBookMapper.toDto(orderedBook);
        return ResponseEntity.created(new URI("/api/ordered-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordered-books : Updates an existing orderedBook.
     *
     * @param orderedBookDTO the orderedBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderedBookDTO,
     * or with status 400 (Bad Request) if the orderedBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderedBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordered-books")
    @Timed
    public ResponseEntity<OrderedBookDTO> updateOrderedBook(@RequestBody OrderedBookDTO orderedBookDTO) throws URISyntaxException {
        log.debug("REST request to update OrderedBook : {}", orderedBookDTO);
        if (orderedBookDTO.getId() == null) {
            return createOrderedBook(orderedBookDTO);
        }
        OrderedBook orderedBook = orderedBookMapper.toEntity(orderedBookDTO);
        orderedBook = orderedBookRepository.save(orderedBook);
        OrderedBookDTO result = orderedBookMapper.toDto(orderedBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderedBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordered-books : get all the orderedBooks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderedBooks in body
     */
    @GetMapping("/ordered-books")
    @Timed
    public List<OrderedBookDTO> getAllOrderedBooks() {
        log.debug("REST request to get all OrderedBooks");
        List<OrderedBook> orderedBooks = orderedBookRepository.findAll();
        return orderedBookMapper.toDto(orderedBooks);
        }

    /**
     * GET  /ordered-books/:id : get the "id" orderedBook.
     *
     * @param id the id of the orderedBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderedBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordered-books/{id}")
    @Timed
    public ResponseEntity<OrderedBookDTO> getOrderedBook(@PathVariable Long id) {
        log.debug("REST request to get OrderedBook : {}", id);
        OrderedBook orderedBook = orderedBookRepository.findOne(id);
        OrderedBookDTO orderedBookDTO = orderedBookMapper.toDto(orderedBook);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderedBookDTO));
    }

    /**
     * DELETE  /ordered-books/:id : delete the "id" orderedBook.
     *
     * @param id the id of the orderedBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordered-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderedBook(@PathVariable Long id) {
        log.debug("REST request to delete OrderedBook : {}", id);
        orderedBookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
