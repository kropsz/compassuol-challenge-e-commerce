package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.PedidoProduto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PedidoCreateDto {
    @NotNull(message = "A lista de produtos não pode ser nula")
    @Size(min = 1, message = "Voce precisa colocar 1 ou mais produtos")
    @Valid
    private List<PedidoProduto> produtos;
    @NotNull(message = "Endreço não pode ser nulo")
    @Valid
    private AddressRequestDto address;
    @NotNull(message = "Método de pagamento não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private Pedido.PaymentMethod paymentMethod;
}
