package com.compassuol.sp.challenge.ecommerce.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    @PostMapping()
    public ResponseEntity<ProdutoResponseDto> create(@RequestBody @Valid ProdutoCreateDto createDto) {
        Produto produto = produtoService.salvar(ProdutoMapper.toProduto(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoMapper.toDto(produto));
    }
    
}
