package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class PedidoMapper {
    public static Pedido toPedido(PedidoCreateDto createDto) {
        return new ModelMapper().map(createDto, Pedido.class);
    }
    public static PedidoResponseDto toDto(Pedido pedido) {
        return new ModelMapper().map(pedido, PedidoResponseDto.class);
    }

    public static List<PedidoResponseDto> toListDto(List<Pedido> pedidos){
        return pedidos.stream().map(orders -> toDto(orders)).collect(Collectors.toList());
    }
}
