package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Buyer;

import com.mycompany.myapp.repository.BuyerRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.BuyerDTO;
import com.mycompany.myapp.service.mapper.BuyerMapper;
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
 * REST controller for managing Buyer.
 */
@RestController
@RequestMapping("/api")
public class BuyerResource {

    private final Logger log = LoggerFactory.getLogger(BuyerResource.class);

    private static final String ENTITY_NAME = "buyer";

    private final BuyerRepository buyerRepository;

    private final BuyerMapper buyerMapper;

    public BuyerResource(BuyerRepository buyerRepository, BuyerMapper buyerMapper) {
        this.buyerRepository = buyerRepository;
        this.buyerMapper = buyerMapper;
    }

    /**
     * POST  /buyers : Create a new buyer.
     *
     * @param buyerDTO the buyerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buyerDTO, or with status 400 (Bad Request) if the buyer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buyers")
    @Timed
    public ResponseEntity<BuyerDTO> createBuyer(@RequestBody BuyerDTO buyerDTO) throws URISyntaxException {
        log.debug("REST request to save Buyer : {}", buyerDTO);
        if (buyerDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        BuyerDTO result = buyerMapper.toDto(buyer);
        return ResponseEntity.created(new URI("/api/buyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buyers : Updates an existing buyer.
     *
     * @param buyerDTO the buyerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buyerDTO,
     * or with status 400 (Bad Request) if the buyerDTO is not valid,
     * or with status 500 (Internal Server Error) if the buyerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buyers")
    @Timed
    public ResponseEntity<BuyerDTO> updateBuyer(@RequestBody BuyerDTO buyerDTO) throws URISyntaxException {
        log.debug("REST request to update Buyer : {}", buyerDTO);
        if (buyerDTO.getId() == null) {
            return createBuyer(buyerDTO);
        }
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        BuyerDTO result = buyerMapper.toDto(buyer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buyerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buyers : get all the buyers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buyers in body
     */
    @GetMapping("/buyers")
    @Timed
    public List<BuyerDTO> getAllBuyers() {
        log.debug("REST request to get all Buyers");
        List<Buyer> buyers = buyerRepository.findAll();
        return buyerMapper.toDto(buyers);
        }

    /**
     * GET  /buyers/:id : get the "id" buyer.
     *
     * @param id the id of the buyerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buyerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/buyers/{id}")
    @Timed
    public ResponseEntity<BuyerDTO> getBuyer(@PathVariable Long id) {
        log.debug("REST request to get Buyer : {}", id);
        Buyer buyer = buyerRepository.findOne(id);
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buyerDTO));
    }

    /**
     * DELETE  /buyers/:id : delete the "id" buyer.
     *
     * @param id the id of the buyerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buyers/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        log.debug("REST request to delete Buyer : {}", id);
        buyerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
