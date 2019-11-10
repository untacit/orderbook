package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderedBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderedBook and its DTO OrderedBookDTO.
 */
@Mapper(componentModel = "spring", uses = {BookMapper.class, OrderBookDomainMapper.class})
public interface OrderedBookMapper extends EntityMapper<OrderedBookDTO, OrderedBook> {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "orderBookDomain.id", target = "orderBookDomainId")
    OrderedBookDTO toDto(OrderedBook orderedBook);

    @Mapping(source = "bookId", target = "book")
    @Mapping(source = "orderBookDomainId", target = "orderBookDomain")
    OrderedBook toEntity(OrderedBookDTO orderedBookDTO);

    default OrderedBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderedBook orderedBook = new OrderedBook();
        orderedBook.setId(id);
        return orderedBook;
    }
}
