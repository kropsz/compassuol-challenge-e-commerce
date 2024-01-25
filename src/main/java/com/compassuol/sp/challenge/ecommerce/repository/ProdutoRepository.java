package com.compassuol.sp.challenge.ecommerce.repository;


import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByName(String name);

}

