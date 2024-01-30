package com.compassuol.sp.challenge.ecommerce.services;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    @Transactional
    public Pedido salvar(Pedido orders) {

        return pedidoRepository.save(orders);
    }

        public List<Pedido> filtrarPorStatus(StatusPedido status) {
            if (status == null) {
                return pedidoRepository.findAll();
            } else {
                return pedidoRepository.findByStatus(status);
            }
        }
    }

