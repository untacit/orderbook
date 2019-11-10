package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskSelectStoreDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskSelectStoreMapper;
import com.mycompany.myapp.service.mapper.OrderBookProcessMapper;
import com.mycompany.myapp.service.mapper.OrderBookDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TaskSelectStore.
 */
@Service
@Transactional
public class TaskSelectStoreService {

    private final Logger log = LoggerFactory.getLogger(TaskSelectStoreService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskSelectStoreMapper taskSelectStoreMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskSelectStore.
     *
     * @param taskSelectStoreDTO the entity to save
     * @return the persisted entity
     */
    public TaskSelectStoreDTO save(TaskSelectStoreDTO taskSelectStoreDTO) {
        log.debug("Request to save TaskSelectStore : {}", taskSelectStoreDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskSelectStoreDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskSelectStoreMapper.copyFromTaskDTOToProcessInstanceDTO(taskSelectStoreDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskSelectStoreDTO;
    }
    

    /**
     * Get one taskSelectStore by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskSelectStoreDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskSelectStore : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskSelectStoreMapper.toDto(orderBookProcess);
    }

}
