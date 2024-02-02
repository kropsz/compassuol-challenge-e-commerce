package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.services.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Pedidos", description = "Contém todas as operações relacionadas ao recurso de pedidos")
@RestController
@RequestMapping("/orders")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar um novo pedido",
            description = "Cria um novo pedido no sistema")
    @ApiResponse(responseCode = "201",
            description = "Pedido criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class)))
    @PostMapping
    public ResponseEntity<PedidoResponseDto> create(@RequestBody @Valid PedidoCreateDto createDto) {
        Pedido pedido = pedidoService.salvar(PedidoMapper.toPedido(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toDto(pedido));
    }

    @Operation(summary = "Buscar pedido por ID",
            description = "Recupera informações sobre um pedido com base no ID fornecido")
    @ApiResponse(responseCode = "200",
            description = "Pedido encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Pedido não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(PedidoMapper.toDto(pedido));
    }

    @Operation(summary = "Recuperar todos os pedidos",
            description = "Recupera todos os pedidos no sistema")
    @ApiResponse(responseCode = "200",
            description = "Pedidos recuperados com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class)))
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAll(@RequestParam(required = false) Pedido.Status status) {
        List<Pedido> pedidos = pedidoService.getAllPedidos(status);
        return ResponseEntity.ok(PedidoMapper.toListDto(pedidos));
    }

    @Operation(summary = "Atualizar um pedido",
            description = "Atualiza as informações de um pedido existente com base no ID fornecido")
    @ApiResponse(responseCode = "200",
            description = "Pedido atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Pedido não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoUpdateDto pedidoDto) {
        Pedido pedidoUpdateFromDto = PedidoMapper.toPedido(pedidoDto);
        Pedido pedidoAtualizado = pedidoService.updatePedido(id, pedidoUpdateFromDto);
        return ResponseEntity.status(HttpStatus.OK).body(PedidoMapper.toDto(pedidoAtualizado));
    }

    @Operation(summary = "Cancelar um pedido",
            description = "Cancela um pedido existente com base no ID fornecido")
    @ApiResponse(responseCode = "200",
            description = "Pedido cancelado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Pedido não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    @PostMapping("/{id}/cancel")
    public ResponseEntity<PedidoResponseDto> cancelarPedido(@PathVariable Long id, @RequestBody PedidoCancelDto cancelReason) {
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, cancelReason.getCancelReason());
        return ResponseEntity.ok(PedidoMapper.toDto(pedidoCancelado));
    }
}
