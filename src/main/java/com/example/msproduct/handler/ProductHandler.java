package com.example.msproduct.handler;

import com.example.msproduct.model.entities.Product;
import com.example.msproduct.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Component
public class ProductHandler {

    @Autowired
    private ProductService productService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
                .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return errorHandler(
                productService.findById(id).flatMap(p -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON_UTF8)
                                .syncBody(p))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Product> bill = request.bodyToMono(Product.class);
        return bill.flatMap(p-> {
                    return productService.create(p);
                }).flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
                        .contentType(APPLICATION_JSON_UTF8)
                        .syncBody(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON_UTF8)
                                .syncBody(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> body = new HashMap<>();
                body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
                body.put("timestamp", new Date());
                body.put("status", errorResponse.getStatusCode().value());
                return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
            }
            return Mono.error(errorResponse);
        });
    }
}
