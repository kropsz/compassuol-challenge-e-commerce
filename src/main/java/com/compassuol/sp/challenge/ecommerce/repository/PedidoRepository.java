package com.compassuol.sp.challenge.ecommerce.repository;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
