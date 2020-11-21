package io.github.cosminseceleanu.tutorials.sampleapp.user.repository;

import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRepository implements UserRepository {
  private final static Map<String, User> USERS = new ConcurrentHashMap<>();

  @Override
  public Mono<User> findById(String id) {
    return Mono.justOrEmpty(USERS.get(id));
  }

  @Override
  public Mono<User> save(User user) {
    if (user.getId() == null) {
      return Mono.error(new IllegalArgumentException("User id can not be null"));
    }
    return Mono.fromSupplier(() -> {
      USERS.put(user.getId(), user);
      return user;
    });
  }

  @Override
  public Flux<User> findAll() {
    return Flux.fromIterable(USERS.values());
  }
}
