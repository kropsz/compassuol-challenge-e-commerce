package com.compassuol.sp.challenge.ecommerce.web.exception;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String path;
    private String method;
    private String message;
    private List<ErrorDetail> details;

    private Map<String, String> errors;
    public ErrorMessage(String message, HttpServletRequest request, HttpStatus status) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.message = status.getReasonPhrase();
    }

    public ErrorMessage(String message, HttpServletRequest request, BindingResult result, HttpStatus status) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.message = status.getReasonPhrase();
        addErrors(result);
    }

    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}

