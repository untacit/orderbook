package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskHandleOrderDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskHandleOrder and its DTO TaskHandleOrderDTO.
 */
@Service
public class TaskHandleOrderMapper {


    public TaskHandleOrderDTO toDto(OrderBookProcess orderBookProcess) {
        TaskHandleOrderDTO dto = new TaskHandleOrderDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        dto.setOrderStatus(orderBookProcess.getOrderBookDomain().getOrderStatus());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskHandleOrderDTO taskHandleOrderDTO, OrderBookProcessDTO orderBookProcessDTO) {
        orderBookProcessDTO.getOrderBookDomain().setOrderStatus(taskHandleOrderDTO.getOrderStatus());
    }

}