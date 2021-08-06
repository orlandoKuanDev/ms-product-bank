package com.example.msproduct.services;

import com.example.msproduct.repositories.IRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T, ID> {
    Mono<T> create(T o);

    Flux<T> findAll();

    Mono<T> findById(ID id);

    Mono<T> update(T o);

    Mono<Void> delete(ID id);

}