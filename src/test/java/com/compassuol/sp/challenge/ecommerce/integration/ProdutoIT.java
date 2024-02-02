package com.compassuol.sp.challenge.ecommerce.integration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.services.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.name=application-test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoIT {
    
    @Autowired
    WebTestClient webTestClient;

    @SpyBean
    private ProdutoService produtoService;


    @Test
    public void createProduto(){
        ProdutoCreateDto produto = ProdutoConstants.PRODUTO_VALID_DTO;
       ProdutoResponseDto responseBody = webTestClient.post()
                .uri("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(produto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDto.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(2L);
    }

    @Test
    public void createProduto_ComNomeJaExistente(){
    
        Produto produto = ProdutoConstants.PRODUTO;
        Mockito.when(produtoService.salvar(produto)).thenReturn(produto);

       ProdutoCreateDto produtoCreate = ProdutoConstants.PRODUTO_INVALID_NAME_DTO;
    
       ErrorMessage responseBody = webTestClient.post()
                .uri("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(produtoCreate)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getCode()).isEqualTo(409);
    }

    @Test
    public void createProduto_ComDadosInvalidos(){

       ProdutoCreateDto produtoCreate = ProdutoConstants.PRODUTO_INVALID_DTO;
    
       ErrorMessage responseBody = webTestClient.post()
                .uri("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(produtoCreate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getCode()).isEqualTo(422);
    }

}
