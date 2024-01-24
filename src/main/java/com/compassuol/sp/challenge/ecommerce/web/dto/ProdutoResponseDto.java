package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ProdutoResponseDto {
    private Long id;
    private String name;
    private Long value;
    private String description;
}
