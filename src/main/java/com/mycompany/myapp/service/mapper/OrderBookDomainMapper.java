package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderBookDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderBookDomain and its DTO OrderBookDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {BuyerMapper.class, StoreMapper.class})
public interface OrderBookDomainMapper extends EntityMapper<OrderBookDomainDTO, OrderBookDomain> {

    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "buyer.name", target = "buyerName")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "store.name", target = "storeName")
    OrderBookDomainDTO toDto(OrderBookDomain orderBookDomain);

    @Mapping(target = "purchasedBooks", ignore = true)
    @Mapping(source = "buyerId", target = "buyer")
    @Mapping(source = "storeId", target = "store")
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
