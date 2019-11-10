package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentDetails and its DTO PaymentDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentDetailsMapper extends EntityMapper<PaymentDetailsDTO, PaymentDetails> {


    @Mapping(target = "orderBookDomain", ignore = true)
    PaymentDetails toEntity(PaymentDetailsDTO paymentDetailsDTO);

    default PaymentDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setId(id);
        return paymentDetails;
    }
}
