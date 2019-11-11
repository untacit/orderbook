package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskSetBuyerInfoDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskSetBuyerInfo and its DTO TaskSetBuyerInfoDTO.
 */
@Service
public class TaskSetBuyerInfoMapper {


    public TaskSetBuyerInfoDTO toDto(OrderBookProcess orderBookProcess) {
        TaskSetBuyerInfoDTO dto = new TaskSetBuyerInfoDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        dto.setShipTo(orderBookProcess.getOrderBookDomain().getShipTo());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskSetBuyerInfoDTO taskSetBuyerInfoDTO, OrderBookProcessDTO orderBookProcessDTO) {
        orderBookProcessDTO.getOrderBookDomain().setShipTo(taskSetBuyerInfoDTO.getShipTo());
        orderBookProcessDTO.getOrderBookDomain().setBuyerId(taskSetBuyerInfoDTO.getBuyerId());
    }

}