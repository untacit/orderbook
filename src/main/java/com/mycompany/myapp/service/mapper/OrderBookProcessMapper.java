package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderBookProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderBookProcess and its DTO OrderBookProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderBookProcessMapper extends EntityMapper<OrderBookProcessDTO, OrderBookProcess> {

    OrderBookProcessDTO toDto(OrderBookProcess orderBookProcess);

    OrderBookProcess toEntity(OrderBookProcessDTO orderBookProcessDTO);

    default OrderBookProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderBookProcess orderBookProcess = new OrderBookProcess();
        orderBookProcess.setId(id);
        return orderBookProcess;
    }

}
