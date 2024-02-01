package com.compassuol.sp.challenge.ecommerce.services;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.exception.CancelamentoInvalidoException;
import com.compassuol.sp.challenge.ecommerce.exception.PedidoNaoEncontradoException;
import com.compassuol.sp.challenge.ecommerce.exception.PedidoUpdateErrorException;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public List<Pedido> getAllPedidos(Pedido.Status status) {
        if (status != null) {
            return pedidoRepository.findAllByStatusOrderByCreatedDateDesc(status);
        } else {
            return pedidoRepository.findAllByOrderByCreatedDateDesc();
        }
    }

    @Transactional
    public Pedido cancelarPedido (Long id, String cancelReason) {
        Pedido pedidoParaCancelar = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("O pedido não foi encontrado"));

        Duration duration = Duration.between(pedidoParaCancelar.getCreatedDate(), LocalDateTime.now());
        long daysSinceCreation = (duration.toHours() + 23) / 24;

        if (daysSinceCreation > 90) {
            throw new CancelamentoInvalidoException("O pedido não pode ser cancelado, data de criação superior à 90 dias");
        } else if ((pedidoParaCancelar.getStatus().equals(Pedido.Status.SENT))) {
            throw new CancelamentoInvalidoException("O pedido não pode ser cancelado, porque já foi enviado");
        } else {
            pedidoParaCancelar.setStatus(Pedido.Status.CANCELED);
            pedidoParaCancelar.setCancelReason(cancelReason);
            pedidoParaCancelar.setCancelDate(LocalDateTime.now());
            return pedidoRepository.save(pedidoParaCancelar);
        }
    }
    
    @Transactional
    public Pedido updatePedido(Long id, Pedido pedidoUpdateData) {
        Pedido pedidoUpdate = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("O pedido não foi encontrado"));
        
        if (pedidoUpdateData.getStatus() == Pedido.Status.CANCELED) {
            throw new PedidoUpdateErrorException("O pedido está cancelado, não pode ser atualizado");
        }

        pedidoUpdate.setStatus(pedidoUpdateData.getStatus());

        return pedidoRepository.save(pedidoUpdate);
    }
}

