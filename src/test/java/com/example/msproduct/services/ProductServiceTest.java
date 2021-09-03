package com.example.msproduct.services;

import com.example.msproduct.data.DataProvider;
import com.example.msproduct.handler.ProductHandler;
import com.example.msproduct.model.entities.Rules;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.model.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private final IProductRepository repository;

    @InjectMocks
    private final IProductService productService;


    ProductServiceTest(IProductRepository repository, IProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Test
    void getAllProducts() {
        Product productRequest1 = DataProvider.ProductRequest();
        Product productRequest2 = DataProvider.ProductRequest();
        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(productRequest1,productRequest2));

        Flux<Product> allProducts = productService.findAll();

        StepVerifier.create(allProducts)
                .expectNext(productRequest1,productRequest2)
                .verifyComplete();
    }
    @Test
    void findByProductName() {
        Product productRequest = DataProvider.ProductRequest();

        Mockito.when(repository.findById(productRequest.getProductName()))
                .thenReturn(Mono.just(productRequest));

        Mono<Product> productName = productService.findByProductName(productRequest.getProductName());

        StepVerifier.create(productName)
                .expectNext(productRequest).verifyComplete();
    }
}