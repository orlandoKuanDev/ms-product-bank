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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsProductApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ProductHandlerTest {

    @Autowired
    RouterConfig routes;
    IProductRepository repository;
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
    private final Predicate<Product> findByProductNamePred =
            product -> ("AHORRO".equals(product.getProductName()));

    private final Predicate<Product> findByProductIdPred =
            product -> ("2".equals(product.getId()));

    private final Predicate<Product> updateProductPred =
            product -> (product.equals(DataProvider.ProductUpdate()));
    @Test
    void whenFindAllProducts_thenCorrectProducts() {
        Product product1 = DataProvider.ProductUpdate();
        Product product2 = DataProvider.ProductResponse();

        List<Product> products = Arrays.asList(
                product2,
                product1);

        Mockito.when(mockProductService.findAll())
                .thenReturn(Flux.just(product1, product2));

        Flux<Product> productFlux = Flux.fromIterable(products);
        given(mockProductService.findAll()).willReturn(productFlux);

        webTestClient
                .get()
                .uri("/product")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class)
                .isEqualTo(products);
                //.hasSize(2);
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
                .expectNextMatches(findByProductNamePred)
                .expectComplete()
                .verify();
    }

    @Test
    void givenProductId_thenCorrectProduct() {
        String productId = "2";
        Product productResponse = DataProvider.ProductResponse();

        Mockito.when(mockProductService.findById(productId))
                .thenReturn(Mono.just(productResponse));

        webTestClient
                .method(HttpMethod.GET)
                .uri("/product/find/" + productId)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody()// it is a Flux<MyUser>
                .as(StepVerifier::create)
                .expectNextMatches(findByProductIdPred)
                .expectComplete()
                .verify();
    }

    @Test
    void whenCreateProduct_thenProductCreated() {
        Product productRequest = DataProvider.ProductRequest();
        Product productResponse = DataProvider.ProductCreate();

        Mockito.when(mockProductService.create(productRequest))
                .thenReturn(Mono.just(productResponse));

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

    @Test
    void whenUpdateProduct_thenProductUpdated() {
        String productId = "1";
        Product productRequest = DataProvider.ProductRequest();
        productRequest.getRules().setCustomerTypeTarget(DataProvider.customerTypeTarget("ENTERPRISE"));
        Product productResponse = DataProvider.ProductUpdate();

        Mockito.when(mockProductService.update(productRequest))
                .thenReturn(Mono.just(productResponse));

        webTestClient
                .method(HttpMethod.PUT)
                .uri("/product/"+ productId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Product.class)
                .getResponseBody()// it is a Flux<MyUser>
                .as(StepVerifier::create)
                .expectNextMatches(updateProductPred)
                .expectComplete()
                .verify();
    }
    @Test
    void whenDeleteProduct_thenProductDeleted() {
        String productId = "1";
        Product productRequest = DataProvider.ProductRequest();

        Mockito.when(mockProductService.delete(productRequest.getId())).thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/product/" + productId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}