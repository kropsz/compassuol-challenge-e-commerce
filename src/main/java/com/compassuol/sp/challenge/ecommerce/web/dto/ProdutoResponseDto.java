package com.compassuol.sp.challenge.ecommerce.web.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ProdutoResponseDto {
    private Long id;
    private String name;
    private BigDecimal value;
    private String description;
}
