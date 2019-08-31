package com.cosmin.tutorials.springreactive.user.repository;

import com.cosmin.tutorials.springreactive.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
