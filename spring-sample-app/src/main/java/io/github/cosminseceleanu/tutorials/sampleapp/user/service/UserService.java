package io.github.cosminseceleanu.tutorials.sampleapp.user.service;

import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;
import io.github.cosminseceleanu.tutorials.sampleapp.user.repository.UserRepository;
import io.github.cosminseceleanu.tutorials.sampleapp.user.exception.UserNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Component
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> get(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    public Mono<User> create(User user) {
        return userRepository.save(user.toBuilder().id(UUID.randomUUID().toString()).build());
    }
}
