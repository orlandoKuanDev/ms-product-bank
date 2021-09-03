package com.example.msproduct.handler;

import com.example.msproduct.config.RouterConfig;
import com.example.msproduct.data.DataProvider;
import com.example.msproduct.model.entities.Product;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.services.IProductService;
import com.example.msproduct.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@Slf4j
@Disabled
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductHandlerTest {
    private static final String FIND_PRODUCT_NAME = "/product/name/{productName}";
    RouterConfig routes;
    private IProductRepository repository;
    ProductService mockProductService;
    ProductHandler mockProductHandler;
    WebTestClient webTestClient;
    @BeforeEach
    void setUp() {
        routes = new RouterConfig();
        repository = Mockito.mock(IProductRepository.class);
        mockProductService = new ProductService(repository);
        //mockProductService = Mockito.mock(ProductService.class);
        mockProductHandler = new ProductHandler(mockProductService);
        mockProductHandler = Mockito.mock(ProductHandler.class);;;
        webTestClient = WebTestClient
                .bindToRouterFunction(routes.rutas(mockProductHandler))
                .build();
    }
    private Predicate<Product> findByProducNamePred =
            product -> ("AHORRO".equals(product.getProductName()));
    @Test
    void findByProductName() {
        String productName = "AHORRO";
        Product productRequest = DataProvider.ProductRequest();
        Product productResponse = DataProvider.ProductResponse();

        Mockito.when(mockProductService.findByProductName(productName))
                .thenReturn(Mono.just(productResponse));

        webTestClient
                .method(HttpMethod.GET)
                //.uri(FIND_PRODUCT_NAME, productName)
                .uri("/product/name/" + productName)
                //.accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Product.class)
                /*.returnResult(Product.class)
                .getResponseBody()// it is a Flux<MyUser>
                .as(StepVerifier::create)
                .expectNextMatches(findByProducNamePred)
                .expectComplete()
                .verify()*/;
    }

    @Test
    void save() {
    }
}