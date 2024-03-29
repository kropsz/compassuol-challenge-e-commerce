package com.compassuol.sp.challenge.ecommerce.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repo.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import com.compassuol.sp.challenge.ecommerce.exception.*;


@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService productService ;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void updateProduto_successfullyUpdates_WithValidData() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(PRODUTO));
        Long id = 1L;
        Produto sut = productService.updateProduto(id, ProdutoMapper.toProduto(PRODUTO_VALID_DTO));

        assertThat(sut).isNotEqualTo(PRODUTO);
    }

    @Test
    public void updateProduto_failsToUpdate_WithInvalidId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        Long id = 1L;

        assertThatThrownBy(() -> productService.updateProduto(id, ProdutoMapper.toProduto(PRODUTO_VALID_DTO))).isInstanceOf(ProdutoNotFoundException.class);
    }

   @Test
    public void createProduto_successfull_WithValidData_ReturnsProduto() {

        Produto expectedProduto = ProdutoConstants.PRODUTO;
        when(produtoRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenReturn(expectedProduto);


        Produto actualProduto = productService.salvar(ProdutoMapper.toProduto(PRODUTO_VALID_DTO));

        assertEquals(expectedProduto, actualProduto);
    }

    @Test
    public void createProduto_fail_WithInvalidData_ReturnsProduto() {
        
        when(produtoRepository.findByName(any(String.class))).thenReturn(Optional.of(ProdutoConstants.PRODUTO));
        assertThrows(ProductNameUniqueViolation.class, () -> {
            productService.salvar(ProdutoMapper.toProduto(PRODUTO_INVALID_DTO));
        });
    }

    @Test
    public void getProdutos_ReturnAllProducts() {
        List<Produto> products = new ArrayList<>(){{
            add(PRODUTO);
        }};
        when(produtoRepository.findAll()).thenReturn(products);
        List<Produto> sut = productService.getAllProdutos();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PRODUTO);
    }

    @Test
    public void getProdutos_ReturnNoProducts() {
        when(produtoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Produto> sut = productService.getAllProdutos();

        assertThat(sut).isEmpty();
    }

    @Test
    public void deleteProduto_successfullyDeletes_WithValidId() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        Long id = 1L;

        productService.deleteProduto(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteProduto_failsToDelete_WithInvalidId() {
        when(produtoRepository.existsById(1L)).thenReturn(false);
        Long id = 1L;

        assertThatThrownBy(() -> productService.deleteProduto(id)).isInstanceOf(ProdutoNotFoundException.class);
    }
    @Test
    public void getById_succefull_WithValidId_returnProduct() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(PRODUTO));
        Produto sut = productService.buscarPorId(1L);
        assertThat(sut).isEqualTo(PRODUTO);
    }

}

