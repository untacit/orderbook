package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Store;

import com.mycompany.myapp.repository.StoreRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.StoreDTO;
import com.mycompany.myapp.service.mapper.StoreMapper;
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
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "store";

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public StoreResource(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    @Timed
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save Store : {}", storeDTO);
        if (storeDTO.getId() != null) {
            throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        StoreDTO result = storeMapper.toDto(store);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 400 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    @Timed
    public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            return createStore(storeDTO);
        }
        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        StoreDTO result = storeMapper.toDto(store);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/stores")
    @Timed
    public List<StoreDTO> getAllStores() {
        log.debug("REST request to get all Stores");
        List<Store> stores = storeRepository.findAll();
        return storeMapper.toDto(stores);
        }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    @Timed
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Store store = storeRepository.findOne(id);
        StoreDTO storeDTO = storeMapper.toDto(store);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(storeDTO));
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
