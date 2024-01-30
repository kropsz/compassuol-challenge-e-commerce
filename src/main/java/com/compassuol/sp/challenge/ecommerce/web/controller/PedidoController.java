package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.services.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping()
    public ResponseEntity<PedidoResponseDto> create(@RequestBody @Valid PedidoCreateDto createDto) {
        Pedido pedido = pedidoService.salvar(PedidoMapper.toPedido(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toDto(pedido));
    }
}
