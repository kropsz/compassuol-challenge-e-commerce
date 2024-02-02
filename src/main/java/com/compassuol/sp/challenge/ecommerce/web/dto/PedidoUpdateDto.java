package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PedidoUpdateDto {
    private Pedido.Status status;
}
