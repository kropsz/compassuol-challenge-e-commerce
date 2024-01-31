package com.compassuol.sp.challenge.ecommerce.repository;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    public List<Pedido> findAllByOrderByCreatedDateDesc();
    public List<Pedido> findAllByStatusOrderByCreatedDateDesc(Pedido.Status status);
    List<Pedido> findByStatus(Pedido status);
}

