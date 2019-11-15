package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskSelectStoreDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskSelectStore and its DTO TaskSelectStoreDTO.
 */
@Service
public class TaskSelectStoreMapper {


    public TaskSelectStoreDTO toDto(OrderBookProcess orderBookProcess) {
        TaskSelectStoreDTO dto = new TaskSelectStoreDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskSelectStoreDTO taskSelectStoreDTO, OrderBookProcessDTO orderBookProcessDTO) {
    }

}