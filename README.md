# e-Commerce

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
