package com.compassuol.sp.challenge.ecommerce.services;

import com.compassuol.sp.challenge.ecommerce.entities.Address;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.entities.Pedido.Status;
import com.compassuol.sp.challenge.ecommerce.exception.ConectionException;
import com.compassuol.sp.challenge.ecommerce.feign.ViaCepFeign;
import com.compassuol.sp.challenge.ecommerce.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.util.DescontoPedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.ViaCepDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.AddressMapper;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ViaCepFeign viaCepFeign;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        BigDecimal subtotalValue = BigDecimal.ZERO;
        for (PedidoProduto pedidoProduto : pedido.getProdutos()) {
                Produto produto = produtoService.buscarPorId(pedidoProduto.getIdProduto());
                BigDecimal produtoValue = produto.getValue();
                subtotalValue = subtotalValue
                .add(produtoValue.multiply(BigDecimal.valueOf(pedidoProduto.getQuantidadeProduto(), 4)));
            }

        try {
            ViaCepDto cep = viaCepFeign.bucarEnderecoCep(pedido.getAddress().getPostalCode());
            Address address = AddressMapper.toAddressModel(pedido, cep);
            DescontoPedido desconto = DescontoPedido.conferirPromocao(pedido.getPaymentMethod(), subtotalValue);
            pedido = constroiPedido(pedido, desconto, address, subtotalValue);
        } catch (FeignException ex) {
            throw new ConectionException("");
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido constroiPedido(Pedido pedido, DescontoPedido descontoPedido, Address address, BigDecimal subtotalValue){
        pedido.setAddress(address);
            pedido.setSubtotalValue(subtotalValue);
            pedido.setDiscount(descontoPedido.getDiscount());
            pedido.setTotalValue(descontoPedido.getTotalValue());
            pedido.setCancelReason("");
            pedido.setStatus(Status.CONFIRMED);
            pedido.setCreatedDate(LocalDateTime.now());
            return pedido;
        }

}