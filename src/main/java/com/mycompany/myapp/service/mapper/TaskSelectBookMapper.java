package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderBookProcess;
import com.mycompany.myapp.service.dto.TaskSelectBookDTO;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;
import org.springframework.stereotype.Service;


/**
 * Mapper for the entity TaskSelectBook and its DTO TaskSelectBookDTO.
 */
@Service
public class TaskSelectBookMapper {


    public TaskSelectBookDTO toDto(OrderBookProcess orderBookProcess) {
        TaskSelectBookDTO dto = new TaskSelectBookDTO();
        dto.setOrderBookProcessId(orderBookProcess.getId());
        dto.setOrderBookProcessBusinessKey(orderBookProcess.getBusinessKey());
        return dto;
    }


    public void copyFromTaskDTOToProcessInstanceDTO(TaskSelectBookDTO taskSelectBookDTO, OrderBookProcessDTO orderBookProcessDTO) {
    }

}