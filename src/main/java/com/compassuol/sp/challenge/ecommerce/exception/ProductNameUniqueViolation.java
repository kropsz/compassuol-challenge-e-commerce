package com.compassuol.sp.challenge.ecommerce.exception;

public class ProductNameUniqueViolation extends RuntimeException{
    public ProductNameUniqueViolation(String message){
        super(message);
    }
}
