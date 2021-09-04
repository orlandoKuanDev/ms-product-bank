package com.example.msproduct.handler;

import com.example.msproduct.MsProductApplication;
import com.example.msproduct.config.RouterConfig;
import com.example.msproduct.data.DataProvider;
import com.example.msproduct.model.entities.Product;
import com.example.msproduct.repositories.IProductRepository;
import com.example.msproduct.services.IProductService;
import com.example.msproduct.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
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
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsProductApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ProductHandlerTest {
    private static final String FIND_PRODUCT_NAME = "/product/name/{productName}";
    @Autowired
    RouterConfig routes;
    private IProductRepository repository;
    ProductService mockProductService;
    ProductHandler mockProductHandler;
    WebTestClient webTestClient;
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(IProductRepository.class);
        mockProductService = new ProductService(repository);
        mockProductHandler = new ProductHandler(mockProductService);
        mockProductHandler = Mockito.mock(ProductHandler.class);;;
        webTestClient = WebTestClient
                .bindToRouterFunction(routes.rutas(mockProductHandler))
                .build();
    }
    private Predicate<Product> findByProducNamePred =
            product -> ("AHORRO".equals(product.getProductName()));

    private Predicate<Product> findByProducIdPred =
            product -> ("1".equals(product.getId()));
    @Test
    void whenFindAllProducts_thenCorrectProducts() {
        webTestClient
                .get()
                .uri("/product")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class)
                .hasSize(2);
    }

    @Test
    void givenProductName_thenCorrectProduct() {
        String productName = "AHORRO";
        Product productResponse = DataProvider.ProductResponse();

        Mockito.when(mockProductService.findByProductName(productName))
                .thenReturn(Mono.just(productResponse));

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

    @Test
    void givenProductId_thenCorrectProduct() {
        String productId = "1";
        Product productResponse = DataProvider.ProductResponse();

        Mockito.when(mockProductService.findById(productId))
                .thenReturn(Mono.just(productResponse));

        webTestClient
                .method(HttpMethod.GET)
                .uri("/product/" + productId)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody()// it is a Flux<MyUser>
                .as(StepVerifier::create)
                .expectNextMatches(findByProducIdPred)
                .expectComplete()
                .verify();
    }

    @Test
    void whenCreateProduct_thenProductCreated() {
        Product productRequest = DataProvider.ProductRequest();
        Product productResponse = DataProvider.ProductCreate();
        Mockito.when(mockProductService.create(productRequest)).thenReturn(Mono.just(productResponse));
        webTestClient
                .method(HttpMethod.POST)
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(productResponse.getId());
    }

}