# e-Commerce

| E-Mail                               | Usuário Github |
|--------------------------------------|----------------|
| pedro.guedes.pb@compasso.com.br      | kropsz         |
| francis.sa.pb@compasso.com.br        | Kitricorvus    |
| edson.paschoal.pb@compasso.com.br    | sshEdd1e       |
| luan.nazareth.pb@compasso.com.br     | luanelder01    |
| bruno.vendruscolo.pb@compasso.com.br | Vendru         |

O projeto consiste no desenvolvimento de uma API REST para um e-commerce, utilizando as
tecnologias e conhecimentos aprendidos no curso. projeto desenvolvido pela equipe NullPointeException no Programa de Bolsa - CompassUOL.

## Tecnologias Utilizadas

- Spring Boot 3
- Java 17
- Maven
- Mockito
- JUnit
- ModelMapper
- Lombok
- MySQL
- JPA
- DevTools
- Spring OpenFeign

# Funcionalidades

## Produto

### Listar Produtos

- **Método:** `GET`
- **Endpoint:** `/products`
- **Descrição:** Retorna todos os produtos cadastrados.

### Buscar Produto por ID

- **Método:** `GET`
- **Endpoint:** `/products/:id`
- **Descrição:** Retorna as informações de um produto específico.

### Cadastrar Produto

- **Método:** `POST`
- **Endpoint:** `/products`
- **Descrição:** Cria um novo produto.

### Atualizar Produto por ID

- **Método:** `PUT`
- **Endpoint:** `/products/id`
- **Descrição:** Atualiza as informações de um produto existente.

### Excluir Produto por ID

- **Método:** `DELETE`
- **Endpoint:** `/products/id`
- **Descrição:** Exclui um produto existente.

## Pedido

### Listar Pedidos

- **Método:** `GET`
- **Endpoint:** `/orders`
- **Descrição:** Retorna a lista de pedidos ordenados por data de criação, dos mais recentes para os mais antigos. É possível filtrar por status do pedido.

### Buscar Pedido por ID

- **Método:** `GET`
- **Endpoint:** `/orders/id`
- **Descrição:** Retorna as informações de um pedido específico.

### Cadastrar Pedido

- **Método:** `POST`
- **Endpoint:** `/orders`
- **Descrição:** Cria um novo pedido.

### Atualizar Pedido por ID

- **Método:** `PUT`
- **Endpoint:** `/orders/id`
- **Descrição:** Atualiza as informações de um pedido existente, como status ou data de entrega.

### Cancelar Pedido por ID

- **Método:** `POST`
- **Endpoint:** `/orders/id/cancel`
- **Descrição:** Cancela um pedido existente.

### Configuração do Projeto
- Clone o repositório.
- Abra o projeto em sua IDE favorita.
- Configure as propriedades do banco de dados em application.properties.
- Execute o projeto usando sua IDE ou Maven.
- Status do Projeto
- O projeto está em desenvolvimento.