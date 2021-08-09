package com.example.msproduct.config;

import com.example.msproduct.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> rutas(ProductHandler handler){
        return route(GET("/product"), handler::findAll)
                .andRoute(GET("/product/find/{productId}"), handler::findById)
                .andRoute(POST("/product"), handler::save)
                .andRoute(PUT("/product/{id}"), handler::update)
                .andRoute(DELETE("/product/{id}"), handler::delete)
                .andRoute(GET("/product/name/{productName}"), handler::findByProductName);

    }
}
