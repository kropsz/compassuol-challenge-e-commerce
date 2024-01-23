package com.compassuol.sp.challenge.ecommerce.exception;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

public class ErrorMessage {
    private int code;
    private String path;
    private HttpStatus status;
    private String method;
    private String message;
    private List<ErrorDetail> details;

    private Map<String, String> errors;

    @ToString
    public ErrorMessage(HttpStatus status, String path, String method, String message, List<ErrorDetail> details) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.status = status.value();
        this.method = request.getMethod();
        this.message = status.getReasonPhrase();
        this.details = details;
    }

    public ErrorMessage(HttpStatus status, String path, String method, String message, List<ErrorDetail> details, BindingResult result) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.status = status.value();
        this.method = request.getMethod();
        this.message = status.getReasonPhrase();
        this.details = details;
        addErrors(result);
    }

    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}