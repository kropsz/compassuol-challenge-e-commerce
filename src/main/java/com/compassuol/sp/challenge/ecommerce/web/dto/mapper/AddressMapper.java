package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.entities.Address;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.ViaCepDto;

public class AddressMapper {
    
    public static Address toAddressModel(Pedido request, ViaCepDto cep) {
        return Address.builder()
                .number(request.getEndereço().getNumber())
                .complement(cep.getComplemento())
                .city(cep.getLocalidade())
                .state(cep.getUf())
                .postalCode(cep.getCep())
                .street(request.getEndereço().getStreet())
                .build();
}
}