package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import org.javatuples.Pair;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class PedidoMapper {
    public static Pair<Pedido, List<PedidoProduto>> toPedido(PedidoCreateDto createDto) {
        return new Pair<Pedido, List<PedidoProduto>>(new ModelMapper().map(createDto, Pedido.class), PedidoProdutoMapper.toPedidoProdutoList(createDto.getProducts()));
    }
    public static PedidoResponseDto toDto(Pedido pedido, List<PedidoProduto> produtos) {
        PropertyMap<Pedido, PedidoResponseDto> props = new PropertyMap<Pedido, PedidoResponseDto>() {
            @Override
            protected void configure() {
                map().setProducts(produtos);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(pedido, PedidoResponseDto.class);
    }
}
