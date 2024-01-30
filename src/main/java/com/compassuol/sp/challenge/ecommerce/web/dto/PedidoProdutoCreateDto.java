package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoProdutoCreateDto {
    @NonNull
    @Positive
    private Long idProduto;
    @NotNull
    @Positive
    private Long quantidadeProduto;
}
