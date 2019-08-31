package com.cosmin.tutorials.springreactive.user.controller;

import com.cosmin.tutorials.springreactive.user.rest.UserResource;
import com.cosmin.tutorials.springreactive.user.service.UserService;
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
    public Mono<UserResource> get(@PathVariable("id") String id) {
        return userService.get(id).map(UserResource::from);
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserResource> create(@RequestBody @Valid UserResource user) {
        return userService.create(user.toModel()).map(UserResource::from);
    }
}
