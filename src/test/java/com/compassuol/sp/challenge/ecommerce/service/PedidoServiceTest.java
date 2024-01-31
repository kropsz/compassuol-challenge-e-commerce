package com.compassuol.sp.challenge.ecommerce.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.PedidoConstants.*;
import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.compassuol.sp.challenge.ecommerce.entities.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.services.PedidoService;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import com.compassuol.sp.challenge.ecommerce.exception.*;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    
    @InjectMocks
    private PedidoService pedidoService ;

    @Mock
    private PedidoRepository pedidoRepository;

    //TODO: AMOSTRAS DE TESTES CRIADOS, REVISAR DEPOIS
    @Test
    public void cancelOrder_successfullyCancel_WithValidId() {
        Pedido sut = PEDIDO;

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(sut));
        Long id = 1L;
        String cancelReason = "teste";
        pedidoService.cancelarPedido(id, cancelReason);
        
        assertThat(PEDIDO.getStatus()).isEqualTo(Status.CANCELED);
    }


    @Test
    public void cancelOrder_failToCancel_WithInvalidStatus() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(PEDIDO_ENVIADO));
        Long id = 1L;
        String cancelReason = "teste";
        
        assertThatThrownBy(() -> pedidoService.cancelarPedido(id, cancelReason)).isInstanceOf(CancelamentoInvalidoException.class).hasMessageContaining("O pedido não pode ser cancelado");
    }

    @Test
    public void cancelOrder_failToCancel_WithInvalidDate() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(PEDIDO_EXPIRADO));
        Long id = 1L;
        String cancelReason = "teste";
        assertThatThrownBy(() -> pedidoService.cancelarPedido(id, cancelReason)).isInstanceOf(CancelamentoInvalidoException.class).hasMessageContaining("O pedido não pode ser cancelado");
    }

    @Test
    public void calcelOrder_fail_WithInvalidId() {
        assertThatThrownBy(() -> pedidoService.cancelarPedido(0L, "teste"))
        .isInstanceOf(PedidoNaoEncontradoException.class)
        .hasMessageContaining("O pedido não foi encontrado");
    }
}
