package com.mobile.mobile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mobile.mobile.model.cart;

public interface cartRepository extends MongoRepository<cart, String> {
}
