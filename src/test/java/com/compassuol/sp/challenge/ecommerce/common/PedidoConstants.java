package com.compassuol.sp.challenge.ecommerce.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.compassuol.sp.challenge.ecommerce.entities.*;
import com.compassuol.sp.challenge.ecommerce.web.dto.AddressRequestDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;


public class PedidoConstants {
    public static List<PedidoProduto> produtos = new ArrayList<>();
    public static final PedidoProduto produto = new PedidoProduto(1L, 1);
    
    public static List<PedidoProduto> iniciaLista(List<PedidoProduto> produtos, PedidoProduto produto){
        produtos.add(produto);
        return produtos;
    }

    //TODO: REVISAR AS CONSTANTES
    public static final Pedido PEDIDO = new Pedido(1L,
                                                    iniciaLista(produtos, produto),
                                                    new Address(1L,"street", 4, "complement", "city", "state", "39802077"),
                                                    Pedido.PaymentMethod.GIFT_CARD,
                                                    new BigDecimal(100.0),
                                                    new BigDecimal(0.01),
                                                    new BigDecimal(1000.00),
                                                    LocalDateTime.now().minus(1, ChronoUnit.DAYS),
                                                    Pedido.Status.CONFIRMED,
                                                    null,
                                                    LocalDateTime.now());

    public static final Pedido PEDIDO_ENVIADO = new Pedido(1L,
                                                    produtos,
                                                    new Address(),
                                                    Pedido.PaymentMethod.GIFT_CARD,
                                                    new BigDecimal(100.0),
                                                    new BigDecimal(0.01),
                                                    new BigDecimal(1000.00),
                                                    LocalDateTime.now().minus(1, ChronoUnit.DAYS),
                                                    Pedido.Status.SENT,
                                                    null,
                                                    LocalDateTime.now());

    public static final Pedido PEDIDO_EXPIRADO = new Pedido(1L,
                                                    produtos,
                                                    new Address(),
                                                    Pedido.PaymentMethod.GIFT_CARD,
                                                    new BigDecimal(100.0),
                                                    new BigDecimal(0.01),
                                                    new BigDecimal(1000.00),
                                                    LocalDateTime.now().minus(90, ChronoUnit.DAYS),
                                                    Pedido.Status.SENT,
                                                    null,
                                                    LocalDateTime.now());
    
    public static final Pedido PEDIDO_CANCELADO = new Pedido(1L,
                                                    produtos,
                                                    new Address(),
                                                    Pedido.PaymentMethod.GIFT_CARD,
                                                    new BigDecimal(100.0),
                                                    new BigDecimal(0.01),
                                                    new BigDecimal(1000.00),
                                                    LocalDateTime.now().minus(90, ChronoUnit.DAYS),
                                                    Pedido.Status.CANCELED,
                                                    null,
                                                    LocalDateTime.now());

    public static Pedido criarPedido(Long id, Pedido.Status status, int elapsedDays) {

        return new Pedido(id,
                        produtos,
                        new Address(),
                        Pedido.PaymentMethod.GIFT_CARD,
                        new BigDecimal(100.0),
                        new BigDecimal(0.01),
                        new BigDecimal(1000.00),
                        LocalDateTime.now().minus(elapsedDays, ChronoUnit.DAYS),
                        status,
                        null,
                        LocalDateTime.now());
    }

    
    public static final PedidoCreateDto PEDIDO_CREATE = new PedidoCreateDto(iniciaLista(produtos, produto),
            new AddressRequestDto("Rua",
                    4,
                    "39802077"),
            Pedido.PaymentMethod.PIX);

            
    public static final PedidoCreateDto PEDIDO_CREATE_INVALID_CEP = new PedidoCreateDto(iniciaLista(produtos, produto),
    new AddressRequestDto("Rua Exemplo",
            4,
            "000000000"),
    Pedido.PaymentMethod.PIX);

    public static final PedidoCreateDto PEDIDO_CREATE_INVALID = new PedidoCreateDto(iniciaLista(produtos, produto),
    new AddressRequestDto(" ",
            4,
            ""),
    Pedido.PaymentMethod.PIX);

    public static final PedidoCreateDto PEDIDO_VALID_DTO = new PedidoCreateDto(produtos,
            new AddressRequestDto("teste", 73573, "36307240"),
            Pedido.PaymentMethod.PIX);

    public static final PedidoCreateDto PEDIDO_INVALID_PAYMENT_DTO = new PedidoCreateDto(produtos,
            new AddressRequestDto("", 73573, "123456789"),
            Pedido.PaymentMethod.CREDIT_CARD);

    public static final PedidoCreateDto PEDIDO_INVALID_CEP_DTO = new PedidoCreateDto(produtos,
            new AddressRequestDto("", 73573, "1234567"),
            Pedido.PaymentMethod.PIX);
}
