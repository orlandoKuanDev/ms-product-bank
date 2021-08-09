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
        return errorHandler(
                productService.findById(productId).flatMap(p -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> findByProductName(ServerRequest request){
        String productName = request.pathVariable("productName");
        return errorHandler(
                productService.findByProductName(productName).flatMap(p -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(Mono.error(new ArgumentWebClientNotValid(
                                String.format("THE PRODUCT NAME DONT EXIST IN MICRO SERVICE PRODUCT-> %s", productName)
                        )))
        );
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);
        return product.flatMap(productService::create)
                .flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                 .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);
        String id = request.pathVariable("id");
        return errorHandler(
                product
                        .flatMap(p -> {
                            p.setId(id);
                            return productService.update(p);
                        })
                        .flatMap(p-> ServerResponse.created(URI.create("/api/product/".concat(p.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p))
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");
        return errorHandler(
                productService.delete(id).then(ServerResponse.noContent().build())
        );
    }

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> body = new HashMap<>();
                body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
                body.put("timestamp", new Date());
                body.put("status", errorResponse.getStatusCode().value());
                return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(body);
            }
            return Mono.error(errorResponse);
        });
    }

}
