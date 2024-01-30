package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.entities.Address;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PedidoCreateDto {
    private List<PedidoProduto> produtos;
    private AddressRequestDto address;
    private Pedido.PaymentMethod paymentMethod;
}
