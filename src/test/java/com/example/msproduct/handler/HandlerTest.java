package com.example.msproduct.handler;

import com.example.msproduct.config.RouterConfig;
import com.example.msproduct.data.DataProvider;
import com.example.msproduct.model.entities.Product;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment=RANDOM_PORT, properties = {"eureka.client.enabled=false"})
class HandlerTest {
    @Autowired
    RouterConfig configuration;
    WebTestClient webClient;
    @Autowired
    ProductHandler productHandler;
//    @Before
//    public void setUp() {
//        this.configuration = new RouterConfig();
//        this.webClient = WebTestClient.bindToRouterFunction(configuration.rutas(configuration.playerRepository()))
//                .build();
//    }
private Predicate<Product> findByProducNamePred =
        product -> ("AHORRO".equals(product.getProductName()));

    @Test
    void findByProductName() {
        String productName = "AHORRO";
        WebTestClient
                .bindToRouterFunction(configuration.rutas(productHandler))
                .build()
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
