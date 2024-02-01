package com.compassuol.sp.challenge.ecommerce.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.compassuol.sp.challenge.ecommerce.common.PedidoConstants;
import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.entities.Address;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.services.PedidoService;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoUpdateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import com.compassuol.sp.challenge.ecommerce.exception.*;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    
    @InjectMocks
    private PedidoService pedidoService ;

    @Mock
    private PedidoRepository pedidoRepository;

    //TODO: AMOSTRAS DE TESTES CRIADOS, REVISAR DEPOIS


    Pedido pedido;
    Pedido pedido_enviado;
    Pedido pedido_cancelado;
    Pedido pedido_expirado;
    Pedido pedido_status_invalido;

    @BeforeEach
    void setUp() {
        pedido = PedidoConstants.criarPedido(1L, Pedido.Status.CONFIRMED, 1);
        pedido_enviado = PedidoConstants.criarPedido(1L, Pedido.Status.SENT, 1);
        pedido_cancelado = PedidoConstants.criarPedido(1L, Pedido.Status.CANCELED, 1);
        pedido_expirado = PedidoConstants.criarPedido(1L, Pedido.Status.CONFIRMED, 91);
        pedido_status_invalido = PedidoConstants.criarPedido(1L, null, 91);
    }


    @Test
    public void cancelOrder_successfullyCancel_WithValidId() {
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        
        Long id = 1L;
        String cancelReason = "teste";
        pedidoService.cancelarPedido(id, cancelReason);
        Pedido sut = pedidoService.buscarPorId(id);
        
        assertThat(sut.getStatus()).isEqualTo(Pedido.Status.CANCELED);
    }


    @Test
    public void cancelOrder_failToCancel_WithInvalidStatus() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido_enviado));
        Long id = 1L;
        String cancelReason = "teste";
        
        assertThatThrownBy(() -> pedidoService.cancelarPedido(id, cancelReason)).isInstanceOf(CancelamentoInvalidoException.class).hasMessageContaining("O pedido não pode ser cancelado");
    }

    @Test
    public void cancelOrder_failToCancel_WithInvalidDate() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido_expirado));
        Long id = 1L;
        String cancelReason = "teste";
        assertThatThrownBy(() -> pedidoService.cancelarPedido(id, cancelReason)).isInstanceOf(CancelamentoInvalidoException.class).hasMessageContaining("O pedido não pode ser cancelado");
    }

    @Test
    public void cancelOrder_fail_WithInvalidId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.cancelarPedido(1L, "teste"))
        .isInstanceOf(PedidoNaoEncontradoException.class)
        .hasMessageContaining("O pedido não foi encontrado");
    }

    @Test
    public void cancelOrder_fail_withInvalidCancelReason() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        
        assertThatThrownBy(() -> pedidoService.cancelarPedido(1L, ""))
        .isInstanceOf(CancelamentoInvalidoException.class)
        .hasMessageContaining("O motivo para cancelamento deve ser informado");
    }

    @Test
    public void cancelOrder_fail_withNullCancelReason() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        
        assertThatThrownBy(() -> pedidoService.cancelarPedido(1L, null))
        .isInstanceOf(CancelamentoInvalidoException.class)
        .hasMessageContaining("O motivo para cancelamento deve ser informado");
    }

    @Test
    public void updateOrder_fail_WithInvalidId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.updatePedido(1L, pedido))
        .isInstanceOf(PedidoNaoEncontradoException.class)
        .hasMessageContaining("O pedido não foi encontrado");
    }

    @Test
    public void updateOrder_fail_WithInvalidStatus() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido_cancelado));

        assertThatThrownBy(() -> pedidoService.updatePedido(1L, pedido_cancelado))
        .isInstanceOf(PedidoUpdateErrorException.class)
        .hasMessageContaining("O pedido está cancelado, não pode ser atualizado");
    }

    @Test
    public void updateOrder_fail_WithInvalidSentStatus() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido_enviado));

        assertThatThrownBy(() -> pedidoService.updatePedido(1L, pedido_enviado))
        .isInstanceOf(PedidoUpdateErrorException.class)
        .hasMessageContaining("O pedido já foi enviado, não pode ser atualizado");
    }

    @Test
    public void updateOrder_sucessfullyUpdates() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Long id = 1L;
        pedidoService.updatePedido(id, pedido_enviado);
        Pedido sut = pedidoService.buscarPorId(id);
        
        assertThat(sut.getStatus()).isEqualTo(Pedido.Status.SENT);
        assertThat(sut.getStatus()).isNotEqualTo(Pedido.Status.CONFIRMED);
    }

    @Test
    public void updateOrder_fail_WithUnexistentStatus() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThatThrownBy(()-> pedidoService.updatePedido(1L, pedido_status_invalido))
        .isInstanceOf(PedidoUpdateErrorException.class)
        .hasMessageContaining("O status do pedido deve ser informado");
    }

    @Test
    public void findOrder_successfullyFind_WithValidId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Long id = 1L;
        Pedido sut = pedidoService.buscarPorId(id);
        assertThat(pedido).isEqualTo(sut);
    }

    @Test
    public void findOrder_fail_WithUnexistentId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.buscarPorId(1L))
        .isInstanceOf(PedidoNaoEncontradoException.class)
        .hasMessageContaining("Pedido não encontrado");
    }
}