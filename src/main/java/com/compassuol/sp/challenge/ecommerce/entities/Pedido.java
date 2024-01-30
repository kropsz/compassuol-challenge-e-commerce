package com.compassuol.sp.challenge.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private Long idPedido;
    @ElementCollection
    @CollectionTable(name = "faz_pedido", joinColumns = @JoinColumn(name = "order_id"))
    private List<PedidoProduto> produtos;
    @OneToOne
    @JoinColumn(name = "id")
    private Address endereço;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "subtotal_value")
    private BigDecimal subtotalValue;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "total_value")
    private BigDecimal totalValue;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "cancel_reason", length = 400)
    private String cancelReason;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;

    public enum PaymentMethod{
        CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX, OTHER
    }

    public enum Status{
        CONFIRMED, SENT, CANCELED
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(idPedido, pedido.idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + idPedido +
                '}';
    }
}
