package com.compassuol.sp.challenge.ecommerce.common;

import java.math.BigDecimal;

import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;


public class ProdutoConstants {
    public static final Produto PRODUTO = new Produto(1L, "Produto 1", new BigDecimal(10.0), "Descrição do produto 1");
    public static final ProdutoCreateDto PRODUTO_VALID_DTO = new ProdutoCreateDto("TESTE", new BigDecimal(10.0), "Descrição do produto 2");
    public static final ProdutoCreateDto PRODUTO_INVALID_DTO = new ProdutoCreateDto("Produto 3", new BigDecimal(10.0), "3");
    public static final ProdutoCreateDto PRODUTO_INVALID_NAME_DTO = new ProdutoCreateDto("Produto 1", new BigDecimal(10.0), "Descrição do produto 1");
}
