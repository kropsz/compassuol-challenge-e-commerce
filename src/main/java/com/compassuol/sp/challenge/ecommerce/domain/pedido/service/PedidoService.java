package com.compassuol.sp.challenge.ecommerce.domain.pedido.service;

import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido.Status;
import com.compassuol.sp.challenge.ecommerce.exception.ConectionException;
import com.compassuol.sp.challenge.ecommerce.exception.MetodoDePagamentoInvalidoException;
import com.compassuol.sp.challenge.ecommerce.feign.ViaCepFeign;
import com.compassuol.sp.challenge.ecommerce.exception.CancelamentoInvalidoException;
import com.compassuol.sp.challenge.ecommerce.exception.PedidoNaoEncontradoException;
import com.compassuol.sp.challenge.ecommerce.exception.PedidoUpdateErrorException;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repo.PedidoRepository;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.EnumUtils;
import com.compassuol.sp.challenge.ecommerce.util.DescontoPedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.ViaCepDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.AddressMapper;

import feign.FeignException;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ViaCepFeign viaCepFeign;

    @Transactional
    public List<Pedido> getAllPedidos(Pedido.Status status) {
        if (status != null) {
            return pedidoRepository.findAllByStatusOrderByCreatedDateDesc(status);
        } else {
            return pedidoRepository.findAllByOrderByCreatedDateDesc();
        }
    }

    @Transactional
    public Pedido updatePedido(Long id, Pedido pedidoUpdateData) {
        Pedido pedidoUpdate = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("O pedido não foi encontrado"));
        
        if (pedidoUpdateData.getStatus() == null) {
            throw new PedidoUpdateErrorException("O status do pedido deve ser informado");
        }
        if (!EnumUtils.isValidEnum(Pedido.Status.class, pedidoUpdateData.getStatus().name())) {
            throw new PedidoUpdateErrorException("Status informado é inválido");
        }

        if (pedidoUpdate.getStatus() == Pedido.Status.CANCELED) {
            throw new PedidoUpdateErrorException("O pedido está cancelado, não pode ser atualizado");
        } else if (pedidoUpdate.getStatus() == Pedido.Status.SENT) {
            throw new PedidoUpdateErrorException("O pedido já foi enviado, não pode ser atualizado");
        } else {
            pedidoUpdate.setStatus(pedidoUpdateData.getStatus());
            return pedidoRepository.save(pedidoUpdate);
        }
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
          if (!EnumUtils.isValidEnum(Pedido.PaymentMethod.class, pedido.getPaymentMethod().name())) {
            throw new MetodoDePagamentoInvalidoException("Método de pagamento invalido"); 
           } 
        BigDecimal subtotalValue = BigDecimal.ZERO;
        for (PedidoProduto pedidoProduto : pedido.getProdutos()) {
                Produto produto = produtoService.buscarPorId(pedidoProduto.getIdProduto());
                BigDecimal produtoValue = produto.getValue();
                subtotalValue = subtotalValue
                .add(produtoValue.multiply(BigDecimal.valueOf(pedidoProduto.getQuantidadeProduto())));
            }

        try {
            ViaCepDto cep = viaCepFeign.bucarEnderecoCep(pedido.getAddress().getPostalCode());
            Address address = AddressMapper.toAddressModel(pedido, cep);
            DescontoPedido desconto = DescontoPedido.conferirPromocao(pedido.getPaymentMethod(), subtotalValue);
            pedido = constroiPedido(pedido, desconto, address, subtotalValue);
        } catch (FeignException ex) {
            throw new ConectionException("Cep inválido ou erro de conexão com a API externa");
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

    @Transactional
    public Pedido cancelarPedido (Long id, String cancelReason) {
        Pedido pedidoParaCancelar = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("O pedido não foi encontrado"));

        if(cancelReason == null || cancelReason.isEmpty()) {
            throw new CancelamentoInvalidoException("O motivo para cancelamento deve ser informado");
        }

        Duration duration = Duration.between(pedidoParaCancelar.getCreatedDate(), LocalDateTime.now());
        long daysSinceCreation = (duration.toHours() + 23) / 24;

        if (daysSinceCreation > 90) {
            throw new CancelamentoInvalidoException("O pedido não pode ser cancelado, data de criação superior à 90 dias");
        } else if ((pedidoParaCancelar.getStatus().equals(Pedido.Status.SENT)) || pedidoParaCancelar.getStatus().equals(Pedido.Status.CANCELED)) {
            throw new CancelamentoInvalidoException("O pedido não pode ser cancelado, porque já foi enviado ou cancelado");
        } else {
            pedidoParaCancelar.setStatus(Pedido.Status.CANCELED);
            pedidoParaCancelar.setCancelReason(cancelReason);
            pedidoParaCancelar.setCancelDate(LocalDateTime.now());
            return pedidoRepository.save(pedidoParaCancelar);
        }
    }
    
    @Transactional
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
    }
}
