package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PaymentDetails;

import com.mycompany.myapp.repository.PaymentDetailsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;
import com.mycompany.myapp.service.mapper.PaymentDetailsMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing PaymentDetails.
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsResource.class);

    private static final String ENTITY_NAME = "paymentDetails";

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    public PaymentDetailsResource(PaymentDetailsRepository paymentDetailsRepository, PaymentDetailsMapper paymentDetailsMapper) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

    /**
     * POST  /payment-details : Create a new paymentDetails.
     *
     * @param paymentDetailsDTO the paymentDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentDetailsDTO, or with status 400 (Bad Request) if the paymentDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-details")
    @Timed
    public ResponseEntity<PaymentDetailsDTO> createPaymentDetails(@RequestBody PaymentDetailsDTO paymentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentDetails : {}", paymentDetailsDTO);
        if (paymentDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsDTO);
        paymentDetails = paymentDetailsRepository.save(paymentDetails);
        PaymentDetailsDTO result = paymentDetailsMapper.toDto(paymentDetails);
        return ResponseEntity.created(new URI("/api/payment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-details : Updates an existing paymentDetails.
     *
     * @param paymentDetailsDTO the paymentDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentDetailsDTO,
     * or with status 400 (Bad Request) if the paymentDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-details")
    @Timed
    public ResponseEntity<PaymentDetailsDTO> updatePaymentDetails(@RequestBody PaymentDetailsDTO paymentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentDetails : {}", paymentDetailsDTO);
        if (paymentDetailsDTO.getId() == null) {
            return createPaymentDetails(paymentDetailsDTO);
        }
        PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsDTO);
        paymentDetails = paymentDetailsRepository.save(paymentDetails);
        PaymentDetailsDTO result = paymentDetailsMapper.toDto(paymentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-details : get all the paymentDetails.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of paymentDetails in body
     */
    @GetMapping("/payment-details")
    @Timed
    public List<PaymentDetailsDTO> getAllPaymentDetails(@RequestParam(required = false) String filter) {
        if ("orderbookdomain-is-null".equals(filter)) {
            log.debug("REST request to get all PaymentDetailss where orderBookDomain is null");
            return StreamSupport
                .stream(paymentDetailsRepository.findAll().spliterator(), false)
                .filter(paymentDetails -> paymentDetails.getOrderBookDomain() == null)
                .map(paymentDetailsMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all PaymentDetails");
        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findAll();
        return paymentDetailsMapper.toDto(paymentDetails);
        }

    /**
     * GET  /payment-details/:id : get the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-details/{id}")
    @Timed
    public ResponseEntity<PaymentDetailsDTO> getPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get PaymentDetails : {}", id);
        PaymentDetails paymentDetails = paymentDetailsRepository.findOne(id);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentDetailsDTO));
    }

    /**
     * DELETE  /payment-details/:id : delete the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-details/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete PaymentDetails : {}", id);
        paymentDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
