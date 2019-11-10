package com.mycompany.myapp.service;

import com.mycompany.myapp.annotation.UntacitTask;
import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.OrderBookProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderBookProcess.
 */
@Service
@Transactional
public class OrderBookProcessService {

    private final Logger log = LoggerFactory.getLogger(OrderBookProcessService.class);

    private final OrderBookProcessRepository orderBookProcessRepository;

    private final OrderBookProcessMapper orderBookProcessMapper;

    public OrderBookProcessService(OrderBookProcessRepository orderBookProcessRepository, OrderBookProcessMapper orderBookProcessMapper) {
        this.orderBookProcessRepository = orderBookProcessRepository;
        this.orderBookProcessMapper = orderBookProcessMapper;
    }

    /**
     * Save a orderBookProcess.
     *
     * @param orderBookProcessDTO the entity to save
     * @return the persisted entity
     */
    public OrderBookProcessDTO save(OrderBookProcessDTO orderBookProcessDTO) {
        log.debug("Request to save OrderBookProcess : {}", orderBookProcessDTO);
        OrderBookProcess orderBookProcess = orderBookProcessMapper.toEntity(orderBookProcessDTO);
        orderBookProcess = orderBookProcessRepository.save(orderBookProcess);
        return orderBookProcessMapper.toDto(orderBookProcess);
    }

    /**
    * Save a process instance and handling the corresponding task
    */
    @UntacitTask
    public void saveProcessInstance(OrderBookProcessDTO orderBookProcessDTO) {
        OrderBookProcess orderBookProcess = orderBookProcessMapper.toEntity(orderBookProcessDTO);
        orderBookProcessRepository.save(orderBookProcess);
    }

    /**
     * Get all the orderBookProcesses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderBookProcessDTO> findAll() {
        log.debug("Request to get all OrderBookProcesses");
        return orderBookProcessRepository.findAll().stream()
            .map(orderBookProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orderBookProcess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrderBookProcessDTO findOne(Long id) {
        log.debug("Request to get OrderBookProcess : {}", id);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(id);
        return orderBookProcessMapper.toDto(orderBookProcess);
    }

    /**
     * Delete the orderBookProcess by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderBookProcess : {}", id);
        orderBookProcessRepository.delete(id);
    }
}
