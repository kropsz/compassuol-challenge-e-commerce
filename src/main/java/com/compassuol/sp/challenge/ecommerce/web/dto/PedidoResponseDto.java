package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.entities.Address;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PedidoResponseDto {
    private Long idPedido;
    private List<PedidoProduto> produtos;
    private Address address;
    private Pedido.PaymentMethod paymentMethod;
    private BigDecimal subtotalValue;
    private BigDecimal discount;
    private BigDecimal totalValue;
    private LocalDateTime createdDate;
    private Pedido.Status status;
}
