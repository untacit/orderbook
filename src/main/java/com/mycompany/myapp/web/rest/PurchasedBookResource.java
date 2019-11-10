package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PurchasedBook;

import com.mycompany.myapp.repository.PurchasedBookRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.PurchasedBookDTO;
import com.mycompany.myapp.service.mapper.PurchasedBookMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PurchasedBook.
 */
@RestController
@RequestMapping("/api")
public class PurchasedBookResource {

    private final Logger log = LoggerFactory.getLogger(PurchasedBookResource.class);

    private static final String ENTITY_NAME = "purchasedBook";

    private final PurchasedBookRepository purchasedBookRepository;

    private final PurchasedBookMapper purchasedBookMapper;

    public PurchasedBookResource(PurchasedBookRepository purchasedBookRepository, PurchasedBookMapper purchasedBookMapper) {
        this.purchasedBookRepository = purchasedBookRepository;
        this.purchasedBookMapper = purchasedBookMapper;
    }

    /**
     * POST  /purchased-books : Create a new purchasedBook.
     *
     * @param purchasedBookDTO the purchasedBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchasedBookDTO, or with status 400 (Bad Request) if the purchasedBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchased-books")
    @Timed
    public ResponseEntity<PurchasedBookDTO> createPurchasedBook(@Valid @RequestBody PurchasedBookDTO purchasedBookDTO) throws URISyntaxException {
        log.debug("REST request to save PurchasedBook : {}", purchasedBookDTO);
        if (purchasedBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchasedBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasedBook purchasedBook = purchasedBookMapper.toEntity(purchasedBookDTO);
        purchasedBook = purchasedBookRepository.save(purchasedBook);
        PurchasedBookDTO result = purchasedBookMapper.toDto(purchasedBook);
        return ResponseEntity.created(new URI("/api/purchased-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchased-books : Updates an existing purchasedBook.
     *
     * @param purchasedBookDTO the purchasedBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchasedBookDTO,
     * or with status 400 (Bad Request) if the purchasedBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchasedBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchased-books")
    @Timed
    public ResponseEntity<PurchasedBookDTO> updatePurchasedBook(@Valid @RequestBody PurchasedBookDTO purchasedBookDTO) throws URISyntaxException {
        log.debug("REST request to update PurchasedBook : {}", purchasedBookDTO);
        if (purchasedBookDTO.getId() == null) {
            return createPurchasedBook(purchasedBookDTO);
        }
        PurchasedBook purchasedBook = purchasedBookMapper.toEntity(purchasedBookDTO);
        purchasedBook = purchasedBookRepository.save(purchasedBook);
        PurchasedBookDTO result = purchasedBookMapper.toDto(purchasedBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchasedBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchased-books : get all the purchasedBooks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of purchasedBooks in body
     */
    @GetMapping("/purchased-books")
    @Timed
    public List<PurchasedBookDTO> getAllPurchasedBooks() {
        log.debug("REST request to get all PurchasedBooks");
        List<PurchasedBook> purchasedBooks = purchasedBookRepository.findAll();
        return purchasedBookMapper.toDto(purchasedBooks);
        }

    /**
     * GET  /purchased-books/:id : get the "id" purchasedBook.
     *
     * @param id the id of the purchasedBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchasedBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchased-books/{id}")
    @Timed
    public ResponseEntity<PurchasedBookDTO> getPurchasedBook(@PathVariable Long id) {
        log.debug("REST request to get PurchasedBook : {}", id);
        PurchasedBook purchasedBook = purchasedBookRepository.findOne(id);
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(purchasedBook);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchasedBookDTO));
    }

    /**
     * DELETE  /purchased-books/:id : delete the "id" purchasedBook.
     *
     * @param id the id of the purchasedBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchased-books/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchasedBook(@PathVariable Long id) {
        log.debug("REST request to delete PurchasedBook : {}", id);
        purchasedBookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
