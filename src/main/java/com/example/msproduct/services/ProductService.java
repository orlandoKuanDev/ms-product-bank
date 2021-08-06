package com.example.msproduct.services;

import com.example.msproduct.model.entities.Product;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService extends BaseService<Product, String> implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    protected IRepository<Product, String> getRepository() {
        return repository;
    }

}

