package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.repository.OrderBookProcessRepository;
import com.mycompany.myapp.service.dto.TaskSetBuyerInfoDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import com.mycompany.myapp.service.mapper.TaskSetBuyerInfoMapper;
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
 * Service Implementation for managing TaskSetBuyerInfo.
 */
@Service
@Transactional
public class TaskSetBuyerInfoService {

    private final Logger log = LoggerFactory.getLogger(TaskSetBuyerInfoService.class);

    @Autowired
    private OrderBookProcessRepository orderBookProcessRepository;

    @Autowired
    private OrderBookProcessService orderBookProcessService;

    @Autowired
    private TaskSetBuyerInfoMapper taskSetBuyerInfoMapper;

    @Autowired
    private OrderBookProcessMapper orderBookProcessMapper;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;


    /**
     * Save a taskSetBuyerInfo.
     *
     * @param taskSetBuyerInfoDTO the entity to save
     * @return the persisted entity
     */
    public TaskSetBuyerInfoDTO save(TaskSetBuyerInfoDTO taskSetBuyerInfoDTO) {
        log.debug("Request to save TaskSetBuyerInfo : {}", taskSetBuyerInfoDTO);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findOne(taskSetBuyerInfoDTO.getOrderBookProcessId());
        OrderBookProcessDTO orderBookProcessDTO = orderBookProcessMapper.toDto(orderBookProcess);
        orderBookProcessDTO.setOrderBookDomain(orderBookDomainMapper.toDto(orderBookProcess.getOrderBookDomain()));
        taskSetBuyerInfoMapper.copyFromTaskDTOToProcessInstanceDTO(taskSetBuyerInfoDTO, orderBookProcessDTO);
        orderBookProcessService.saveProcessInstance(orderBookProcessDTO);
        return taskSetBuyerInfoDTO;
    }
    

    /**
     * Get one taskSetBuyerInfo by processInstanceId.
     *
     * @param processInstanceId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TaskSetBuyerInfoDTO findByProcessInstanceId(String processInstanceId) {
        log.debug("Request to get TaskSetBuyerInfo : {}", processInstanceId);
        OrderBookProcess orderBookProcess = orderBookProcessRepository.findByCamundaProcessInstanceId(processInstanceId);
        return taskSetBuyerInfoMapper.toDto(orderBookProcess);
    }

}
