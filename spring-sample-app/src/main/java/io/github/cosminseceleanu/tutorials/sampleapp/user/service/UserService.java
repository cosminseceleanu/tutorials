package io.github.cosminseceleanu.tutorials.sampleapp.user.service;

import io.github.cosminseceleanu.tutorials.sampleapp.user.exception.UserNotFoundException;
import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;
import io.github.cosminseceleanu.tutorials.sampleapp.user.repository.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
