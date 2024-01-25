package com.compassuol.sp.challenge.ecommerce.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
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

    @Operation(summary = "Criar um novo produto", description = "Recurso para criar um novo produto",
                responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Produto ja registrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Recurso não processado por entrada de dados invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping()
    public ResponseEntity<ProdutoResponseDto> create(@RequestBody @Valid ProdutoCreateDto createDto) {
        Produto produto = produtoService.salvar(ProdutoMapper.toProduto(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoMapper.toDto(produto));
    }

    @Operation(summary = "Operação de atualizar as informações de um produto", description = "Operação de atualizar as informações de um produto",
                parameters = {
                    @Parameter(in = PATH, name = "id", description = "Número identificador do produto a ser atualizado", required = true),
                },
                responses = {
                    @ApiResponse(responseCode = "200",
                                description = "Produto atualzado com sucesso",
                                content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                                description = "Identificador de produto inexistente",
                                content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                                description = "Produto não processado por entrada de dados invalidos",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })

    @PutMapping("/{id}")	
    public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoCreateDto produto) {
        Produto produtoUpdateFromDto = ProdutoMapper.toProduto(produto);
        Produto updatedProduto = produtoService.updateProduto(id, produtoUpdateFromDto);
        return ResponseEntity.status(HttpStatus.OK).body(ProdutoMapper.toDto(updatedProduto));
    }
    
}
