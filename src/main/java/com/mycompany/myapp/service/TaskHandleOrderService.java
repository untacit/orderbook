package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskHandleOrderDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskHandleOrderMapper;
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
 * Service Implementation for managing TaskHandleOrder.
 */
@Service
@Transactional
public class TaskHandleOrderService {

    private final Logger log = LoggerFactory.getLogger(TaskHandleOrderService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskHandleOrderMapper taskHandleOrderMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskHandleOrder.
     *
     * @param taskHandleOrderDTO the entity to save
     * @return the persisted entity
     */
    public TaskHandleOrderDTO save(TaskHandleOrderDTO taskHandleOrderDTO) {
        log.debug("Request to save TaskHandleOrder : {}", taskHandleOrderDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskHandleOrderDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskHandleOrderMapper.copyFromTaskDTOToProcessInstanceDTO(taskHandleOrderDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskHandleOrderDTO;
    }
    

    /**
     * Get one taskHandleOrder by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskHandleOrderDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskHandleOrder : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskHandleOrderMapper.toDto(orderBookProcess);
    }

}
