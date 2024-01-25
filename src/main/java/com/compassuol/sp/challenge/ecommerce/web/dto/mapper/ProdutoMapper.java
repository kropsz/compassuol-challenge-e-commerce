package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class ProdutoMapper {

    public static Produto toProduto(ProdutoCreateDto createDto){
        return new ModelMapper().map(createDto, Produto.class);
    }
    public static ProdutoResponseDto toDto(Produto produto){
        return new ModelMapper().map(produto, ProdutoResponseDto.class);
    }

    public static List<ProdutoResponseDto> toListDto(List<Produto> produtos){
        return produtos.stream().map(prod -> toDto(prod)).collect(Collectors.toList());
    }
}
