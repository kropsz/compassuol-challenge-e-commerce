package com.compassuol.sp.challenge.ecommerce.domain.pedido.repo;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    public List<Pedido> findAllByOrderByCreatedDateDesc();
    public List<Pedido> findAllByStatusOrderByCreatedDateDesc(Pedido.Status status);
    List<Pedido> findByStatus(Pedido status);
}

