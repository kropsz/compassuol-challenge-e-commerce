package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.entities.PedidoProduto;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoProdutoMapper {
    public static PedidoProduto toPedidoProduto(PedidoProdutoCreateDto createDto){
        return new ModelMapper().map(createDto, PedidoProduto.class);
    }
    public static List<PedidoProduto> toPedidoProdutoList(List<PedidoProdutoCreateDto> createDto) {
        return createDto.stream().map(ordProd -> toPedidoProduto(ordProd)).collect(Collectors.toList());
    }
}
