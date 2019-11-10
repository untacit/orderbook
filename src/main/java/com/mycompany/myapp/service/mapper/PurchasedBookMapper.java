package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PurchasedBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchasedBook and its DTO PurchasedBookDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderBookDomainMapper.class})
public interface PurchasedBookMapper extends EntityMapper<PurchasedBookDTO, PurchasedBook> {

    @Mapping(source = "orderBookDomain.id", target = "orderBookDomainId")
    PurchasedBookDTO toDto(PurchasedBook purchasedBook);

    @Mapping(source = "orderBookDomainId", target = "orderBookDomain")
    PurchasedBook toEntity(PurchasedBookDTO purchasedBookDTO);

    default PurchasedBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setId(id);
        return purchasedBook;
    }
}
