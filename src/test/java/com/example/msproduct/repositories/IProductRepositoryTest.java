package com.example.msproduct.repositories;

import com.example.msproduct.data.DataProvider;
import com.example.msproduct.model.entities.Product;
import com.example.msproduct.model.entities.Rules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class IProductRepositoryTest {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    IProductRepositoryTest(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Test
    void ProductRepositoryTest() {
        Product productRequest1 = DataProvider.ProductRequest();
        Product productRequest2 = DataProvider.ProductRequest();

        Flux<Product> saved = Flux
                .just(productRequest1,productRequest2)
                .flatMap(this.reactiveMongoTemplate::save);

        Flux<Product> interaction = this.reactiveMongoTemplate
                .dropCollection(Product.class)
                .thenMany(saved)
                .thenMany(this.reactiveMongoTemplate.findAll(Product.class));

        Predicate<Product> predicate = productDTO ->
                StringUtils.hasText(productDTO.getId().toString()) && (
                        productDTO.getProductName().equalsIgnoreCase("CUENTA CORRIENTE") ||
                                productDTO.getProductName().equalsIgnoreCase("AHORRO"));

        StepVerifier
                .create(interaction)
                .expectNextMatches(predicate)
                .expectNextMatches(predicate)
                .verifyComplete();
    }
}