package com.compassuol.sp.challenge.ecommerce.domain.produto.service;

import com.compassuol.sp.challenge.ecommerce.domain.produto.model.Produto;
import com.compassuol.sp.challenge.ecommerce.exception.ProductNameUniqueViolation;
import com.compassuol.sp.challenge.ecommerce.exception.ProdutoNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repo.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto salvar(Produto produto) {
        Optional<Produto> existingProduct = produtoRepository.findByName(produto.getName());
        if (existingProduct.isPresent()) {
            throw new ProductNameUniqueViolation(String.format("Produto '%s' já cadastrado", produto.getName()));
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto updateProduto(Long id, Produto produto) {
        Produto produtoUpdate = produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        produtoUpdate.setName(produto.getName());
        produtoUpdate.setDescription(produto.getDescription());
        produtoUpdate.setValue(produto.getValue());

        return produtoRepository.save(produtoUpdate);
    }


    @Transactional
    public void deleteProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new ProdutoNotFoundException("Produto não encontrado");
        }
    }
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new ProdutoNotFoundException("Produto não encontrado.")
        );
    }
}
