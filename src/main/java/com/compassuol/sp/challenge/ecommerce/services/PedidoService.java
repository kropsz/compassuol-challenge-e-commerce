package com.compassuol.sp.challenge.ecommerce.services;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final PedidoRepository pedidoRepository;
    @Transactional
    public Pair<Pedido, List<PedidoProduto>> salvar(Pair<Pedido, List<PedidoProduto>> orders) {
        return new Pair<Pedido, List<PedidoProduto>>(pedidoRepository.save(orders.getValue0()), orders.getValue1().stream().map(order -> pedidoProdutoRepository.save(order)).collect(Collectors.toList()));
    }
}
