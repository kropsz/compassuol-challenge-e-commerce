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

    private void addErrors(BindingResult result){
    this.errors = new HashMap<>();
    for (FieldError fieldError : result.getFieldErrors()){
        this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMethod() {
        return method;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getDetails() {
        return details;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(List<ErrorDetail> details) {
        this.details = details;
    }
}

class ErrorDetail {
    private String field;
    private String message;

    public ErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}