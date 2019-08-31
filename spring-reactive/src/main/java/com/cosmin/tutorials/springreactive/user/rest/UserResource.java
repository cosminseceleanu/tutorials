package com.cosmin.tutorials.springreactive.user.rest;

import com.cosmin.tutorials.springreactive.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserResource {

    public static UserResource from(User user) {
        return new UserResource(user.getId(), user.getName(), user.getEmail());
    }

    public User toModel() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
}
