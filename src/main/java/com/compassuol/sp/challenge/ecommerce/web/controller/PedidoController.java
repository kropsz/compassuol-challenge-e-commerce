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

    @Operation(summary = "Criar um novo Pedido", description = "Recurso para criar um novo pedido",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Pedido criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Erro de validação",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))        
            }
    )
    @PostMapping()
    public ResponseEntity<PedidoResponseDto> create(@RequestBody @Valid PedidoCreateDto createDto) {
        Pedido pedido = pedidoService.salvar(PedidoMapper.toPedido(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toDto(pedido));
    }

    @Operation(summary = "Recuperar um Pedido por ID", description = "Recurso para recuperar um pedido existente por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
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

    @Operation(summary = "Atualizar um Pedido", description = "Recurso para atualizar um pedido existente",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Pedido atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Pedido não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",
                            description = "BadRequest",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Erro de validação",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoUpdateDto pedidoDto) {
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",
                            description = "BadRequest",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping("/{id}/cancel")
    public ResponseEntity<PedidoResponseDto> cancelarPedido(@PathVariable Long id, @RequestBody PedidoCancelDto cancelReason) {
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, cancelReason.getCancelReason());
        return ResponseEntity.ok(PedidoMapper.toDto(pedidoCancelado));
    }
}
