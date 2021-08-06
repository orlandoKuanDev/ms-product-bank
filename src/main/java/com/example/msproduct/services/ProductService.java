package com.example.msproduct.services;

import com.example.msproduct.model.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductService implements IProductService{

    @Autowired
    private IProductService productService;

    @Override
    public Mono<Product> create(Product o) {
        return productService.create(o);
    }

    @Override
    public Flux<Product> findAll() {
        return productService.findAll();
    }

    @Override
    public Mono<Product> findById(String s) {
        return productService.findById(s);
    }

    @Override
    public Mono<Product> update(Product o) {
        return productService.update(o);
    }

    @Override
    public Mono<Void> delete(String s) {
        return productService.delete(s);
    }
}
