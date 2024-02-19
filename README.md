# Challenge E-Commerce (Compass-UOL)
O projeto consiste no desenvolvimento de uma API REST para um e-commerce, utilizando as
tecnologias e conhecimentos aprendidos no curso. A API deverá expor endpoints para as funcionalidades de Pedido e Produto. O projeto foi desenvolvido pela equipe NullPointeException no programa de Bolsas da Compass UOL | Back-end Journey (Spring Boot) - AWS Cloud Context.

| E-Mail                               | Usuário Github |
|--------------------------------------|----------------|
| pedro.guedes.pb@compasso.com.br      | kropsz         |
| francis.sa.pb@compasso.com.br        | Kitricorvus    |
| edson.paschoal.pb@compasso.com.br    | sshEdd1e       |
| luan.nazareth.pb@compasso.com.br     | luanelder01    |
| bruno.vendruscolo.pb@compasso.com.br | Vendru         |


## Tecnologias Utilizadas

- Spring Boot 3
- Java 17
    
### Dependências 
Gerenciador de Dependências : Maven

- Spring Boot 3
- Spring Web
- Spring Validation
- Spring Data JPA
- Spring Doc OpenAPI Swagger
- Spring DevTools
- Spring Cloud OpenFeign
- Spring Boot WebFlux
- Lombok
- ModelMapper
- Mockito
- JUnit 5
- Jacoco
- Banco de dados H2 
- Banco de dados MySQL

## **Regras de Negócio - Geral:**

- Todos os campos data, devem seguir o padrão ISO 8601 (exemplo: 2023-07-20T12:00:00Z ).
- Todos os campos data, devem ser definidas automaticamente.
- As funcionalidades pedido, produto e feedback podem conter: data de cadastro ( created_date ),
  data de atualização ( update_date ) e data de cancelamento ( cancel_date ).
- A documentação da API ViaCEP pode ser encontrada no endereço https://viacep.com.br/

# Domínios
1. Pedido: A funcionalidade Pedido permite que os usuários façam pedidos de produtos de um catálogo.
2. Produto: A funcionalidade Produto permite que os usuários criem, leiam, atualizem e excluam produtos.
### Modelagem do Banco de Dados 
![database](https://github.com/kropsz/compassuol-challenge-e-commerce/assets/114687669/17c99121-a0cf-4826-903f-97c9c62a2033)


# Produto
A funcionalidade Produto permite que os usuários criem, leiam, atualizem e excluam produtos.
### **Regras de negócio:**

- O nome do produto deve ser único.
- A descrição do produto deve ter no mínimo 10 caracteres.
- O valor do produto deve ser um número positivo.

## Endpoints
 Métodos | URL | Descrição |
| --- | --- | --- |
| `GET` | /products | Lista produto: deve retornar todos os produtos. |
| `GET` | /products/{id} | Buscar produto: Retorna as informações de um produto específico. |
| `POST` | /products | Cadastrar pedido: Cria um novo produto. |
| `PUT` | /products/{id} | Atualizar produto: atualiza as informações de um pedido existente. |
| `DELETE` | /products/{id} | Excluir produto: Excluir um produto existente. |

## Payloads
* `REQUEST`  - Utilizado para criação e atualização de um produto.
```JSON
{
"name": "Product name",
"description": "Product description",
"value": 10.5
}
```
* `RESPONSE` - Utilizado para retorno de informações de um produto. Exemplo:
```JSON
{
"id": 1,
"name": "Product name",
"description": "Product description",
"value": 10.5
}
```

# **Pedido**

A funcionalidade Pedido permite que os usuários façam pedidos de produtos de um catálogo. Um pedido é composto por uma lista de produtos, um endereço de entrega e uma forma de pagamento.
### **Regras de negócio:**

- Para completar as informações relativas ao endereço, deve ser consultado os dados na API ViaCEP.
- A operação GET /orders deve estar ordenada por data de criação, dos pedidos mais recentes para os mais antigos. Além disso, deve ser possível filtrar por status do pedido.
- Status dos pedidos: CONFIRMED, SENT, CANCELED
- Tipos permitidos de pagamento: CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX, OTHER.
- O valor total do pedido é calculado pela aplicação de acordo com a seguinte fórmula: total_value = subtotal_value - discount
- O desconto de 5% é aplicado apenas para pedidos com método de pagamento PIX.
- Para o endereço, deve ser informado apenas number , complement e postal_code .
- Para complementar as informações relativas ao endereço, deve ser consultar os dados na API ViaCEP.

### **Regras de negócio para o cancelamento de pedidos:**

- Um pedido só pode ser cancelado se o status for diferente de SENT .
- Um pedido não pode ser cancelado se tiver mais de 90 dias de criação.
- Após o cancelamento, o status do pedido deverá ser alterado para CANCELED .

## Endpoints
| Métodos | URL | Descrição |
| --- | --- | --- |
| `GET` | /orders | Lista pedidos: retorna a lista de pedidos ordenados por data de criação, dos mais recentes para os mais antigos. Além disso, é possível filtrar por status do pedido. |
| `GET` | /orders/{id} | Buscar pedido: Retorna as informações de um pedido específico. |
| `POST` | /orders | Cadastrar pedido: Cria um novo pedido |
| `PUT` | /orders/{id} | Atualizar pedido: atualiza as informações de um pedido existente como status ou data de entrega. |
| `GET` | /orders/{id}/cancel | Cancelar pedido: Cancela um pedido existente. |

## Payloads
* `REQUEST`  - Utilizado para criação de um pedido.
```JSON
{
"products": [
  {
"productId": 1,
"quantity": 2
  },
  {
"productId": 2,
"quantity": 5
  }
],
"address": {
"number": 10,
"complement": "Próximo ao compus da UFOP",
"postalCode": "31333333"
  },
"paymentMethod": "PIX"
}
```

* `RESPONSE`  - Utilizado para retorno de informações de um pedido.
```JSON
{
"id": 1,
"products": [
{
"productId": 1,
"quantity": 2
},
{
"productId": 2,
"quantity": 5
}
],
"address": {
"street": "Street name",
"number": 10,
"complement": "details",
"city": "City name",
"state": "State name",
"postalCode": "31333333"
},
"paymentMethod": "PIX",
"subtotalValue": 100.00,
"discount": 0.5,
"totalValue": 95.00,
"createdDate": "2023-07-20T12:00:00Z",
"status": "CONFIRMED"
}
```
#### Atualizar Pedido
* `REQUEST`  - Utilizado para ATUALIZAR status de um pedido.
```JSON
{
"status": "SENT"
}
```

#### Cancelar Pedido
* `REQUEST`  - Utilizado para CANCELAMENTO de um pedido.
```JSON
{
"cancelReason": "Cancel reason"
}
```
* `RESPONSE`  - Utilizado para receber informações de um pedido cancelado.
```JSON
{
"products": [
  {
"product_id": 1,
"quantity": 2
  },
  {
"product_id": 2,
"quantity": 5
  }
],
"address": {
"street": "Street name",
"number": 10,
"complement": "details",
"city": "City name",
"state": "State name",
"postalCode": "31333333"
  },
"paymentMethod": "PIX",
"subtotalValue": 100.00,
"discount": 0.5,
"totalValue": 95.00,
"createdDate": "2023-07-20T12:00:00Z",
"status": "CANCELED",
"cancelReason": "Cancel reason",
"cancelDate": "2023-07-20T12:00:00Z"
}
```

# Fluxo de Erros
No que diz respeito ao tratamento de exceções, a API segue um fluxo padrão de erros. Este fluxo envolve um payload de resposta conhecido como `ErrorMenssage`. Este payload contém informações como o código do erro, o status, a mensagem e, se disponível, os detalhes do erro.

### **Regras de Negócio para a resposta de exceção:**

A resposta de exceção deve conter as seguintes informações:

- code : Código de status HTTP
- status : Status da resposta
- message : Mensagem de erro

O campo details é opcional e pode ser usado para fornecer informações adicionais sobre o erro.
- Erro de Validação:
```JSON
{
"code": 400,
"status": "Bad Request",
"message": "O campo 'nome' é obrigatório.",
"details": [
  {
"field": "nome",
"message": "O campo 'nome' é obrigatório."
    }
  ]
}
```

- Erro de Negócio:
```JSON
{
"code": 400,
"status": "Bad Request",
"message": "O pedido já foi cancelado.",
"details": []
}
```

- Erro Inesperado:  
```JSON
{
"code": 500,
"status": "Internal Server Error",
"message": "Ocorreu um erro inesperado.",
"details": []
}
```

# Como executar o projeto
### JDK 17
O projeto foi desenvolvido com a linguagem Java, fazendo uso do Java Development Kit (JDK) versão 17. Assim, para rodar o projeto, é necessário ter o JDK 17 instalado no seu computador. Você pode fazer o download do mesmo através do link fornecido -> [Download Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### Escolha a sua IDE:
* VSCode
* IntelliJ
* Spring Tools 4 (Eclipse)
  
### Configurações de Banco de Dados
É necessário  configurar o banco de dados MYSQL:
* Navegue até a pasta  `src/main/resources` onde está o arquivo `application.properties` e altere as propriedades:
<div>
<img src="https://github.com/kropsz/compassuol-challenge-e-commerce/assets/114687669/4b43bd89-7f5a-4b7a-83e9-968af00e1cbc" width="500px" />
</div>

 Substitua as variáveis de ambiente pelos seus dados de acesso ao Banco de Dados: 

*  `spring.datasource.url` : Informe o endereço do seu banco de dados MySql.
*  `spring.datasource.username`: Informe o nome de usuário do banco de dados MySql.
*  `spring.datasource.password`: Informe a senha do seu usuário do banco de dados MySql
  
### Execução
Com o projeto em execução acesse o endereço `http://localhost:8080/e-commerce.html` para acessar a documentação da API pelo Swagger ou utilize uma ferramenta de testes de API's como: 
* Postman
* Insomnia
* Bruno

# Conclusão
O projeto E-Commerce da Compass-UOL desenvolvido pela equipe NullPointeException proporciona uma solução robusta para gerenciamento de pedidos e produtos em um ambiente de e-commerce. Utilizando tecnologias modernas como Spring Boot 3 e Java 17, o projeto incorpora boas práticas de desenvolvimento, incluindo testes unitários e de integração, documentação Swagger, e tratamento de exceções.
