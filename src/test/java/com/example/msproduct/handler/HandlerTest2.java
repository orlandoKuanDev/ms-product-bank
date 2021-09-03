package com.example.msproduct.handler;

import com.example.msproduct.MsProductApplication;
import com.example.msproduct.config.RouterConfig;
import com.example.msproduct.data.DataProvider;
import com.example.msproduct.model.entities.Product;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.services.IProductService;
import com.example.msproduct.services.ProductService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsProductApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HandlerTest2 {
    @Autowired
    RouterConfig configuration;
    @Autowired
    ProductHandler productHandler;
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(configuration.rutas(productHandler))
                .build();
    }
    private Predicate<Product> findByProducNamePred =
            product -> ("AHORRO".equals(product.getProductName()));

    @Test
    void findByProductName() {
        String productName = "AHORRO";
        webTestClient
                .method(HttpMethod.GET)
                .uri("/product/name/" + productName)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody()// it is a Flux<MyUser>
                .as(StepVerifier::create)
                .expectNextMatches(findByProducNamePred)
                .expectComplete()
                .verify();
    }

}
