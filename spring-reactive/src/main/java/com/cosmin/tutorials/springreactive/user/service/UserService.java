package com.cosmin.tutorials.springreactive.user.service;

import com.cosmin.tutorials.springreactive.user.model.User;
import com.cosmin.tutorials.springreactive.user.repository.UserRepository;
import com.cosmin.tutorials.springreactive.user.exception.UserNotFoundException;
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
        return userRepository.save(user);
    }
}
