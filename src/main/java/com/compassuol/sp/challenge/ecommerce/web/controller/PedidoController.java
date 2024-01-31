package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.services.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


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

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(PedidoMapper.toDto(pedido));


    }

    @Operation(summary = "Recuperar todos os Pedidos ordenados por data de criação", description = "Recurso para recuperar todos os pedidos no banco de e ordená-los por data de criação",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Recursos recuperados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Recurso não existe",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAll(@RequestParam(required = false) Pedido.Status status) {
        List<Pedido> pedidos = pedidoService.getAllPedidos(status);
        return ResponseEntity.ok(PedidoMapper.toListDto(pedidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoCreateDto pedidoDto) {
        Pedido pedidoUpdateFromDto = PedidoMapper.toPedido(pedidoDto);
        Pedido pedidoAtualizado = pedidoService.updatePedido(id, pedidoUpdateFromDto);
        return ResponseEntity.status(HttpStatus.OK).body(PedidoMapper.toDto(pedidoAtualizado));
    }

    @Operation(summary = "Cancelar um pedido", description = "Recurso para cancelar um pedido existente",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Pedido cancelado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Pedido não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping("/{id}/cancel")
    public ResponseEntity<PedidoResponseDto> cancelarPedido(@PathVariable Long id, @RequestBody String cancelReason) {
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, cancelReason);
        return ResponseEntity.ok(PedidoMapper.toDto(pedidoCancelado));
    }
}
