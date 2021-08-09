package com.example.msproduct.services;

import com.example.msproduct.model.entities.Product;
import reactor.core.publisher.Mono;

public interface IProductService extends IBaseService<Product, String> {
    Mono<Product> findByProductName(String productName);
}
