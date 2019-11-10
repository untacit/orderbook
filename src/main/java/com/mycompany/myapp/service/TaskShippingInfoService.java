package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskShippingInfoDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskShippingInfoMapper;
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
 * Service Implementation for managing TaskShippingInfo.
 */
@Service
@Transactional
public class TaskShippingInfoService {

    private final Logger log = LoggerFactory.getLogger(TaskShippingInfoService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskShippingInfoMapper taskShippingInfoMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskShippingInfo.
     *
     * @param taskShippingInfoDTO the entity to save
     * @return the persisted entity
     */
    public TaskShippingInfoDTO save(TaskShippingInfoDTO taskShippingInfoDTO) {
        log.debug("Request to save TaskShippingInfo : {}", taskShippingInfoDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskShippingInfoDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskShippingInfoMapper.copyFromTaskDTOToProcessInstanceDTO(taskShippingInfoDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskShippingInfoDTO;
    }
    

    /**
     * Get one taskShippingInfo by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskShippingInfoDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskShippingInfo : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskShippingInfoMapper.toDto(orderBookProcess);
    }

}
