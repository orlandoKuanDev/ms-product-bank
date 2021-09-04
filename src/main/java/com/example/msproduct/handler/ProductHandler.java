package com.example.msproduct.handler;

import com.example.msproduct.exception.ArgumentWebClientNotValid;
import com.example.msproduct.model.entities.Product;
import com.example.msproduct.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ProductHandler {

    private final IProductService productService;

    @Autowired
    public ProductHandler(IProductService productService) {
        this.productService = productService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String productId = request.pathVariable("productId");
        return productService.findById(productId).flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .switchIfEmpty(Mono.error(new ArgumentWebClientNotValid(
                        String.format("THE PRODUCT WITH ID %s DOES NOT EXIST", productId))));
    }

    public Mono<ServerResponse> findByProductName(ServerRequest request){
        String productName = request.pathVariable("productName");
        return productService.findByProductName(productName).flatMap(p -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(p))
                .switchIfEmpty(Mono.error(new ArgumentWebClientNotValid(
                        String.format("THE PRODUCT WITH NAME %s DOES NOT EXIST", productName))));
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);
        return product.flatMap(productService::create)
                .flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);
        String id = request.pathVariable("id");
        return  product
                .flatMap(p -> {
                    p.setId(id);
                    return productService.update(p);
                })
                .flatMap(p-> ServerResponse.created(URI.create("/api/product/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(e -> Mono.error(new RuntimeException(e.getMessage())));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");
        return productService.delete(id).then(ServerResponse.noContent().build())
                .onErrorResume(e -> Mono.error(new RuntimeException(e.getMessage())));
    }

}
