package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskShippingInfoDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskShippingInfo and its DTO TaskShippingInfoDTO.
 */
@Service
public class TaskShippingInfoMapper {


    public TaskShippingInfoDTO toDto(OrderBookProcess orderBookProcess) {
        TaskShippingInfoDTO dto = new TaskShippingInfoDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        dto.setShipTo(orderBookProcess.getOrderBookDomain().getShipTo());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskShippingInfoDTO taskShippingInfoDTO, OrderBookProcessDTO orderBookProcessDTO) {
        orderBookProcessDTO.getOrderBookDomain().setShipTo(taskShippingInfoDTO.getShipTo());
    }

}