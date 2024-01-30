package com.compassuol.sp.challenge.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private Long idPedido;
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "subtotal_value")
    private BigDecimal subtotalValue;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "total_value")
    private BigDecimal totalValue;
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "status")
    private Status status;
    @Column(name = "cancel_reason", length = 400)
    private String cancelReason;
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
