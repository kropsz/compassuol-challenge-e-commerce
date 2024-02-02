package com.compassuol.sp.challenge.ecommerce.domain.produto.repo;


import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByName(String name);

}

