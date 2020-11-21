package io.github.cosminseceleanu.tutorials.sampleapp.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
    String id;
    String email;
    String name;
}
