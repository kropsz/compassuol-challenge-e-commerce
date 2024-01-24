package com.compassuol.sp.challenge.ecommerce.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compassuol.sp.challenge.ecommerce.config.jacoco.ExcludeFromJacocoGeneratedReport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    // TODO: Teste simples para o swagger, remover depois
    @Operation( summary = "Teste",
                description = "Teste",
                responses = {
                    @ApiResponse(
                        responseCode = "201", description = "Teste"
                    )
                })
    @ExcludeFromJacocoGeneratedReport
    @GetMapping("/teste-delete-me")
    public String deleteMeMethod() {
        return "Delete me";
    }    
}
