package com.compassuol.sp.challenge.ecommerce.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.compassuol.sp.challenge.ecommerce.exception.ProductNameUniqueViolation;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage("Campo(s) Inválidos", request, result, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(ProductNameUniqueViolation.class)
    public ResponseEntity<ErrorMessage> productNameUniqueViolation(ProductNameUniqueViolation ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage("O campo nome é Inválido", request, HttpStatus.CONFLICT));
    }
}