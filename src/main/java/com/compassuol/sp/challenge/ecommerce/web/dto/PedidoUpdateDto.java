package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PedidoUpdateDto {
    private List<PedidoProduto> produtos;
    private AddressRequestDto address;
    private Pedido.PaymentMethod paymentMethod;
    private Pedido.Status status;
}
