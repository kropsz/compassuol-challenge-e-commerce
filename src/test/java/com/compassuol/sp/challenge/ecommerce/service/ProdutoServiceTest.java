package com.compassuol.sp.challenge.ecommerce.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
