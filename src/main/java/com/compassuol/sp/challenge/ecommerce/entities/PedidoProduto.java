package com.compassuol.sp.challenge.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class PedidoProduto {

    @Column(name = "product_id")
    @NotNull
    @Positive
    private Long idProduto;
    @NotNull
    @Positive
    private Long quantidadeProduto;

}
