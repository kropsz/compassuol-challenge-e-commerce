package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.util.MoneyDeserializer;
import com.compassuol.sp.challenge.ecommerce.util.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private BigDecimal subtotalValue;
    private BigDecimal discount;
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private BigDecimal totalValue;
    private LocalDateTime createdDate;
    private Pedido.Status status;
}
