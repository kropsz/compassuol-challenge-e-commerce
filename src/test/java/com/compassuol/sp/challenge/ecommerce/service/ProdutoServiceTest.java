package com.compassuol.sp.challenge.ecommerce.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
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
    public void createProduto_successfull_WithValidData_ReturnsPlanet() {

        Produto expectedProduto = ProdutoConstants.PRODUTO;
        when(produtoRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenReturn(expectedProduto);


        Produto actualProduto = productService.salvar(ProdutoMapper.toProduto(PRODUTO_INVALID_DTO));

        assertEquals(expectedProduto, actualProduto);
    }

    @Test
    public void createProduto_fail_WithInvalidData_ReturnsPlanet() {
        
        when(produtoRepository.findByName(any(String.class))).thenReturn(Optional.of(ProdutoConstants.PRODUTO));
        assertThrows(ProductNameUniqueViolation.class, () -> {
            productService.salvar(ProdutoMapper.toProduto(PRODUTO_INVALID_DTO));
        });
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


}

