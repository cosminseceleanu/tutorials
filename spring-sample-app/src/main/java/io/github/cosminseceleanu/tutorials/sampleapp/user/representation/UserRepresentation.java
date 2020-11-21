package io.github.cosminseceleanu.tutorials.sampleapp.user.representation;

import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserRepresentation {

    public static UserRepresentation from(User user) {
        return new UserRepresentation(user.getId(), user.getName(), user.getEmail());
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
