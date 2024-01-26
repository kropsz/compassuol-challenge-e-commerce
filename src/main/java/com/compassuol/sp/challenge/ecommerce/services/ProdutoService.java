package com.compassuol.sp.challenge.ecommerce.services;

import com.compassuol.sp.challenge.ecommerce.entities.Produto;
import com.compassuol.sp.challenge.ecommerce.exception.ProductNameUniqueViolation;
import com.compassuol.sp.challenge.ecommerce.exception.ProdutoNotFoundException;
import com.compassuol.sp.challenge.ecommerce.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    public Produto getProdutoById(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            return optionalProduto.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
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
}
