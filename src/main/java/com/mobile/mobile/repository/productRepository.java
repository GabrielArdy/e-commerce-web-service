package com.mobile.mobile.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mobile.mobile.model.product;

public interface productRepository extends MongoRepository<product, String> {
    @Query("{ 'name': { '$regex': ?0, '$options': 'i' } }")
    List<product> findByNameRegex(String name);

    @Query("{ 'name': { '$regex': ?0, '$options': 'i' }, 'price': { '$gte': ?1, '$lte': ?2 } }")
    List<product> findByNameRegexAndPriceBetween(String name, double minPrice, double maxPrice);
}