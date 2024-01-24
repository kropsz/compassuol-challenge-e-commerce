package com.compassuol.sp.challenge.ecommerce.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
class ErrorDetail {
    private String field;
    private String message;

}