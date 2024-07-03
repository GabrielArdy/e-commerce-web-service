package com.mobile.mobile.repository;

import com.mobile.mobile.model.order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface orderRepository extends MongoRepository<order, String> {
}
