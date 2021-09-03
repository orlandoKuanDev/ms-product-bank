package com.example.msproduct.services;

import com.example.msproduct.data.DataProvider;
import com.example.msproduct.handler.ProductHandler;
import com.example.msproduct.model.entities.Rules;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.model.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private IProductRepository repository;
    ProductService mockProductService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(IProductRepository.class);
        mockProductService = new ProductService(repository);
    }

    @Test
    void findByProductName() {
        
        Product productRequest = DataProvider.ProductRequest();

        Mockito.when(repository.findByProductName(productRequest.getProductName()))
                .thenReturn(Mono.just(productRequest));

        Mono<Product> productName = mockProductService.findByProductName(productRequest.getProductName());
        log.info("PRODUCT_NAME, {}", productName);
        StepVerifier.create(productName)
                .expectNext(productRequest)
                .verifyComplete();
    }
}