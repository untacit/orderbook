package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskPayBookDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskPayBookMapper;
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
 * Service Implementation for managing TaskPayBook.
 */
@Service
@Transactional
public class TaskPayBookService {

    private final Logger log = LoggerFactory.getLogger(TaskPayBookService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskPayBookMapper taskPayBookMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskPayBook.
     *
     * @param taskPayBookDTO the entity to save
     * @return the persisted entity
     */
    public TaskPayBookDTO save(TaskPayBookDTO taskPayBookDTO) {
        log.debug("Request to save TaskPayBook : {}", taskPayBookDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskPayBookDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskPayBookMapper.copyFromTaskDTOToProcessInstanceDTO(taskPayBookDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskPayBookDTO;
    }
    

    /**
     * Get one taskPayBook by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskPayBookDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskPayBook : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskPayBookMapper.toDto(orderBookProcess);
    }

}
