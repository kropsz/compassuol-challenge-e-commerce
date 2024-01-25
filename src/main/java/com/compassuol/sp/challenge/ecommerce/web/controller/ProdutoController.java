package com.compassuol.sp.challenge.ecommerce.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compassuol.sp.challenge.ecommerce.config.jacoco.ExcludeFromJacocoGeneratedReport;
import com.compassuol.sp.challenge.ecommerce.entities.*;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.*;
import com.compassuol.sp.challenge.ecommerce.web.exception.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;


@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
                
    @PutMapping("/{id}")	
    public ResponseEntity<ProdutoResponseDto> putMethodName(@PathVariable Long id, @RequestBody ProdutoCreateDto produto) {
        Produto produtoUpdate = ProdutoMapper.toProduto(produto);
        Produto updateProduto = produtoService.updateProduto(id, produtoUpdate);
        ProdutoResponseDto dto = ProdutoMapper.toDto(updateProduto);
        return ResponseEntity.ok(dto);
    }
}
