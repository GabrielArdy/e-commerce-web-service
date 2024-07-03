package com.mobile.mobile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mobile.mobile.model.user;

public interface userRepository extends MongoRepository<user, String> {
    user findByUsername(String username);

    void deleteByUsername(String username);
}
