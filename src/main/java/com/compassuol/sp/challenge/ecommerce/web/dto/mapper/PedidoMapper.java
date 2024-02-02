package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCancelDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoUpdateDto;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class PedidoMapper {
    public static Pedido toPedido(PedidoCreateDto createDto) {
        return new ModelMapper().map(createDto, Pedido.class);
    }
    public static Pedido toPedido(PedidoUpdateDto updateDto) {
        return new ModelMapper().map(updateDto, Pedido.class);
    }
    public static Pedido toPedido(PedidoCancelDto cancelDto) {
        return new ModelMapper().map(cancelDto, Pedido.class);
    }
    public static PedidoResponseDto toDto(Pedido pedido) {
        return new ModelMapper().map(pedido, PedidoResponseDto.class);
    }
    public static List<PedidoResponseDto> toListDto(List<Pedido> pedidos){
        return pedidos.stream().map(orders -> toDto(orders)).collect(Collectors.toList());
    }
}
