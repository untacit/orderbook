package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskSelectBookDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskSelectBookMapper;
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
 * Service Implementation for managing TaskSelectBook.
 */
@Service
@Transactional
public class TaskSelectBookService {

    private final Logger log = LoggerFactory.getLogger(TaskSelectBookService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskSelectBookMapper taskSelectBookMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskSelectBook.
     *
     * @param taskSelectBookDTO the entity to save
     * @return the persisted entity
     */
    public TaskSelectBookDTO save(TaskSelectBookDTO taskSelectBookDTO) {
        log.debug("Request to save TaskSelectBook : {}", taskSelectBookDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskSelectBookDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskSelectBookMapper.copyFromTaskDTOToProcessInstanceDTO(taskSelectBookDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskSelectBookDTO;
    }
    

    /**
     * Get one taskSelectBook by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskSelectBookDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskSelectBook : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskSelectBookMapper.toDto(orderBookProcess);
    }

}
