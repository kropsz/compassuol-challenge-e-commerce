package com.compassuol.sp.challenge.ecommerce.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.compassuol.sp.challenge.ecommerce.entities.*;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.*;

public class PedidoConstants {
    public static List<PedidoProduto> produtos;

    //TODO: REVISAR AS CONSTANTES
    public static final Pedido PEDIDO = new Pedido(1L,
                                                    produtos,
                                                    new Address(1L,123, "Casa", "João Monlevade", "Minas Gerais", "35930001"),
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
                                                    new Address(1L,123, "Casa", "João Monlevade", "Minas Gerais", "35930001"),
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
                                                    new Address(1L,123, "Casa", "João Monlevade", "Minas Gerais", "35930001"),
                                                    Pedido.PaymentMethod.GIFT_CARD,
                                                    new BigDecimal(100.0),
                                                    new BigDecimal(0.01),
                                                    new BigDecimal(1000.00),
                                                    LocalDateTime.now().minus(90, ChronoUnit.DAYS),
                                                    Pedido.Status.SENT,
                                                    null,
                                                    LocalDateTime.now());
}
