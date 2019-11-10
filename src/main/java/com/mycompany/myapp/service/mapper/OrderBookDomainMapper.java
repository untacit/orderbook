package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderBookDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderBookDomain and its DTO OrderBookDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentDetailsMapper.class, BookMapper.class, BuyerMapper.class, StoreMapper.class})
public interface OrderBookDomainMapper extends EntityMapper<OrderBookDomainDTO, OrderBookDomain> {

    @Mapping(source = "paymentDetails.id", target = "paymentDetailsId")
    OrderBookDomainDTO toDto(OrderBookDomain orderBookDomain);

    @Mapping(source = "paymentDetailsId", target = "paymentDetails")
    @Mapping(target = "purchasedBooks", ignore = true)
    OrderBookDomain toEntity(OrderBookDomainDTO orderBookDomainDTO);

    default OrderBookDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderBookDomain orderBookDomain = new OrderBookDomain();
        orderBookDomain.setId(id);
        return orderBookDomain;
    }
}
