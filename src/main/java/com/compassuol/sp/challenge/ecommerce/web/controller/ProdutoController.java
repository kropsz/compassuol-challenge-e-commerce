package com.compassuol.sp.challenge.ecommerce.web.controller;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


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

    @Operation(summary = "Atualizar as informações de um produto", description = "Operação para realizar atualizações nas informações de um produto conforme o identificador fornecido",
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

    @Operation(summary = "Recuperar um Produto pelo id", description = "Recurso para recuperar um Produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> getByID(@Valid @PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toDto(produto));
    }

    @Operation(summary = "Recuperar todos os Produtos", description = "Recurso para recuperar todos os produtos no banco de dados",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Recursos recuperados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> getAll() {
        List<Produto> prods = produtoService.getAllProdutos();
        return ResponseEntity.ok(ProdutoMapper.toListDto(prods));
    }

    @Operation(
            summary = "Excluir produto",
            description = "Operação para excluir um produto conforme o identificador fornecido",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Número identificador referente ao produto a ser excluído", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto excluído"),
                    @ApiResponse(responseCode = "404", description = "Identificador de produto inexistente"),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}