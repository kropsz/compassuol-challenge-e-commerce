package com.compassuol.sp.challenge.ecommerce.integration;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.compassuol.sp.challenge.ecommerce.common.PedidoConstants;
import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.name=application-test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PedidoIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProdutoService produtoService;
    @SpyBean
    private PedidoService pedidoService;


    @BeforeEach
        public void setUp() {
        Produto produtoFalso = ProdutoConstants.PRODUTO;
        Pedido pedido = PedidoConstants.PEDIDO;
        Mockito.when(produtoService.salvar(produtoFalso)).thenReturn(produtoFalso);
        Mockito.when(produtoService.buscarPorId(1L)).thenReturn(produtoFalso);
        Mockito.when(pedidoService.salvar(pedido)).thenReturn(pedido);
        Mockito.when(pedidoService.buscarPorId(1L)).thenReturn(pedido);
    }

    @Test
    public void testCriacaoPedidoConfirmado() {
        PedidoCreateDto pedido = PedidoConstants.PEDIDO_CREATE;

        PedidoResponseDto responseBody = webTestClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedido)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PedidoResponseDto.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getIdPedido()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getIdPedido()).isEqualTo(2L);   
    }

    @Test
    public void testeCriaçãoPedidoComDadosInválidos_CepInválido(){
        PedidoCreateDto pedido = PedidoConstants.PEDIDO_CREATE_INVALID_CEP;
        ErrorMessage responseBody = webTestClient
        .post()
        .uri("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(pedido)
        .exchange()
        .expectStatus().isEqualTo(500)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCode()).isEqualTo(500);
    }

    @Test
    public void testeCriaçãoPedidoComDadosInválidos(){
        PedidoCreateDto pedido = PedidoConstants.PEDIDO_CREATE_INVALID;
        ErrorMessage responseBody = webTestClient
        .post()
        .uri("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(pedido)
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCode()).isEqualTo(422);
    }

    @Test
    public void testeBuscarPedidoSucesso() {

        PedidoResponseDto responseBody = webTestClient.get()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PedidoResponseDto.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getIdPedido()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getIdPedido()).isEqualTo(1);
        
    }    

    
    @Test
    public void testeBuscarPedido_IdInexistente() {

        ErrorMessage responseBody = webTestClient.get()
                .uri("/orders/100")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getCode()).isEqualTo(404);         
        
    }    
}
