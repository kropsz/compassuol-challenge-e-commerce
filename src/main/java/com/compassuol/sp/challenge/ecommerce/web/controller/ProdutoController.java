package com.compassuol.sp.challenge.ecommerce.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @GetMapping
    public String getMethodName() {
        return "Olá mundo";
    }
    
}
