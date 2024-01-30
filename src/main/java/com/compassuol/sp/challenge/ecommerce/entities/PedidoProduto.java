package com.compassuol.sp.challenge.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "faz_pedido")
@Entity
public class PedidoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedidoProduto;
    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;
    @Column(name = "quantidade")
    private Long quantidade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoProduto that = (PedidoProduto) o;
        return idPedidoProduto.equals(that.idPedidoProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedidoProduto);
    }
}
