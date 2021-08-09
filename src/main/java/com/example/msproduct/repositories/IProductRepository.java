package com.example.msproduct.repositories;

import com.example.msproduct.model.entities.Product;
import reactor.core.publisher.Mono;

public interface IProductRepository extends IRepository<Product, String>{
    Mono<Product> findByProductName(String productName);
}