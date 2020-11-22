package io.github.cosminseceleanu.tutorials.sampleapp.user.repository;

import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findById(String id);

    Mono<User> save(User user);

    Flux<User> findAll();
}
