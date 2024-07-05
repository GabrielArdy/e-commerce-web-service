package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Shipping;
import java.util.List;

public interface ShippingRepository extends MongoRepository<Shipping, String> {
  public List<Shipping> findByReceipt(String receipt);

}
