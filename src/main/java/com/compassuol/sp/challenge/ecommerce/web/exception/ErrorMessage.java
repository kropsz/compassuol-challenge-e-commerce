package com.compassuol.sp.challenge.ecommerce.web.exception;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String path;
    private String method;
    private String status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDetail> details;
    public ErrorMessage(String message, HttpServletRequest request, HttpStatus status) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(String message, HttpServletRequest request, BindingResult result, HttpStatus status) {
        this.code = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.getReasonPhrase(); 
        this.message = message;
        addDetails(result);
    }

    private void addDetails(BindingResult result) {
        this.details = new ArrayList<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setField(fieldError.getField());
            errorDetail.setMessage(fieldError.getDefaultMessage());
            this.details.add(errorDetail);
        }
    }
}
