package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskPayBookDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskPayBook and its DTO TaskPayBookDTO.
 */
@Service
public class TaskPayBookMapper {


    public TaskPayBookDTO toDto(OrderBookProcess orderBookProcess) {
        TaskPayBookDTO dto = new TaskPayBookDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        dto.setNameOnCard(orderBookProcess.getOrderBookDomain().getNameOnCard());
        dto.setCreditCard(orderBookProcess.getOrderBookDomain().getCreditCard());
        dto.setExpiryDate(orderBookProcess.getOrderBookDomain().getExpiryDate());
        dto.setCcv(orderBookProcess.getOrderBookDomain().getCcv());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskPayBookDTO taskPayBookDTO, OrderBookProcessDTO orderBookProcessDTO) {
        orderBookProcessDTO.getOrderBookDomain().setNameOnCard(taskPayBookDTO.getNameOnCard());
        orderBookProcessDTO.getOrderBookDomain().setCreditCard(taskPayBookDTO.getCreditCard());
        orderBookProcessDTO.getOrderBookDomain().setExpiryDate(taskPayBookDTO.getExpiryDate());
        orderBookProcessDTO.getOrderBookDomain().setCcv(taskPayBookDTO.getCcv());
    }

}