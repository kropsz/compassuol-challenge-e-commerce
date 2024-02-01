package com.compassuol.sp.challenge.ecommerce.web.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.compassuol.sp.challenge.ecommerce.exception.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, result, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(ProductNameUniqueViolation.class)
    public ResponseEntity<ErrorMessage> productNameUniqueViolation(ProductNameUniqueViolation ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorMessage> ProdutoNotFoundException(ProdutoNotFoundException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(CancelamentoInvalidoException.class)
    public ResponseEntity<ErrorMessage> CancelamentoInvalidoException(CancelamentoInvalidoException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<ErrorMessage> PedidoNaoEncontradoException(PedidoNaoEncontradoException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.NOT_FOUND));
    }
    
    @ExceptionHandler({ConectionException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessage> ConectionException(RuntimeException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<ErrorMessage> DataInvalidaException(DataInvalidaException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY )
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.UNPROCESSABLE_ENTITY));
    }

     @ExceptionHandler({MetodoDePagamentoInvalidoException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> MetodoDePagamentoInvalidoException(RuntimeException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY )
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.UNPROCESSABLE_ENTITY));
    }
    
    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<ErrorMessage> StatusInvalidoException(StatusInvalidoException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY )
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(PedidoUpdateErrorException.class)
    public ResponseEntity<ErrorMessage> PedidoUpdateErrorException(PedidoUpdateErrorException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST )
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(ex.getMessage(), request, HttpStatus.BAD_REQUEST));
    }
}