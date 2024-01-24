package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ProdutoCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    @Positive
    private Long value;

    @NotBlank
    @Size(min = 10)
    private String description;
}
