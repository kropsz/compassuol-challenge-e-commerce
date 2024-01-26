package com.compassuol.sp.challenge.ecommerce.common;

import java.math.BigDecimal;

import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;

public class ProdutoConstants {
    public static final Produto PRODUTO = new Produto(1L, "Produto 1", new BigDecimal(10.0), "Descrição do produto 1");
    public static final ProdutoCreateDto PRODUTO_VALID_DTO = new ProdutoCreateDto("Produto 2", new BigDecimal(10.0), "Descrição do produto 2");
    public static final ProdutoCreateDto PRODUTO_INVALID_DTO = new ProdutoCreateDto("Produto 3", new BigDecimal(10.0), "3");
}