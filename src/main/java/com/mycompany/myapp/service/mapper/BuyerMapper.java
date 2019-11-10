package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BuyerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Buyer and its DTO BuyerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuyerMapper extends EntityMapper<BuyerDTO, Buyer> {


    @Mapping(target = "orderBookDomains", ignore = true)
    Buyer toEntity(BuyerDTO buyerDTO);

    default Buyer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Buyer buyer = new Buyer();
        buyer.setId(id);
        return buyer;
    }
}
