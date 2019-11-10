package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookDomain;
import com.mycompany.myapp.repository.OrderBookDomainRepository;
import com.mycompany.myapp.service.dto.OrderBookDomainDTO;
import com.mycompany.myapp.service.mapper.OrderBookDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderBookDomain.
 */
@Service
@Transactional
public class OrderBookDomainService {

    private final Logger log = LoggerFactory.getLogger(OrderBookDomainService.class);

    private final OrderBookDomainRepository orderBookDomainRepository;

    private final OrderBookDomainMapper orderBookDomainMapper;

    public OrderBookDomainService(OrderBookDomainRepository orderBookDomainRepository, OrderBookDomainMapper orderBookDomainMapper) {
        this.orderBookDomainRepository = orderBookDomainRepository;
        this.orderBookDomainMapper = orderBookDomainMapper;
    }

    /**
     * Save a orderBookDomain.
     *
     * @param orderBookDomainDTO the entity to save
     * @return the persisted entity
     */
    public OrderBookDomainDTO save(OrderBookDomainDTO orderBookDomainDTO) {
        log.debug("Request to save OrderBookDomain : {}", orderBookDomainDTO);
        OrderBookDomain orderBookDomain = orderBookDomainMapper.toEntity(orderBookDomainDTO);
        orderBookDomain = orderBookDomainRepository.save(orderBookDomain);
        return orderBookDomainMapper.toDto(orderBookDomain);
    }

    /**
     * Get all the orderBookDomains.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderBookDomainDTO> findAll() {
        log.debug("Request to get all OrderBookDomains");
        return orderBookDomainRepository.findAll().stream()
            .map(orderBookDomainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orderBookDomain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrderBookDomainDTO findOne(Long id) {
        log.debug("Request to get OrderBookDomain : {}", id);
        OrderBookDomain orderBookDomain = orderBookDomainRepository.findOne(id);
        return orderBookDomainMapper.toDto(orderBookDomain);
    }

    /**
     * Delete the orderBookDomain by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderBookDomain : {}", id);
        orderBookDomainRepository.delete(id);
    }
}
