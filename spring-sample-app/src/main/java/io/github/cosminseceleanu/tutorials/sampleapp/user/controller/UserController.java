package io.github.cosminseceleanu.tutorials.sampleapp.user.controller;

import io.github.cosminseceleanu.tutorials.sampleapp.user.representation.UserRepresentation;
import io.github.cosminseceleanu.tutorials.sampleapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public Mono<UserRepresentation> get(@PathVariable("id") String id) {
        return userService.get(id).map(UserRepresentation::from);
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserRepresentation> create(@RequestBody @Valid UserRepresentation user) {
        return userService.create(user.toModel()).map(UserRepresentation::from);
    }
}
