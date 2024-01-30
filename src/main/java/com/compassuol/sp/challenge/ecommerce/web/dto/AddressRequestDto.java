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
    @NotEmpty
    private String street;
    @NotNull
    private Integer number;
    @NotEmpty
    @Size(max = 9)
    private String postalCode;
}