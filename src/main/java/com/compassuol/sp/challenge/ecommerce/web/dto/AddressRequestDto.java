package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressRequestDto {
    @NotEmpty(message = "O Complemento não pode ser nulo")
    private String complemento;
    @NotNull
    private Integer number;
    @NotEmpty(message = "O cep não pode ser vazio")
    @Size(min = 8, max = 9)
    private String postalCode;
}
