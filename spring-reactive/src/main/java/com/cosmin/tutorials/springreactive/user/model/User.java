package com.cosmin.tutorials.springreactive.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Data
@Builder
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String email;
}
