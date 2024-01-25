package com.compassuol.sp.challenge.ecommerce.exception;

public class ProdutoNotFoundException extends RuntimeException{
    public ProdutoNotFoundException(String message){
        super(message);
    }
}
